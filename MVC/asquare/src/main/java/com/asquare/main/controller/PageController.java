package com.asquare.main.controller;

import com.asquare.main.dto.ClusterResponse;
import com.asquare.main.dto.CustomerRegistrationRequest;
import com.asquare.main.dto.ProjectResponse;
import com.asquare.main.dto.WingResponse;
import com.asquare.main.entity.Cluster;
import com.asquare.main.entity.Project;
import com.asquare.main.entity.Wing;
import com.asquare.main.repository.BuilderRepository;
import com.asquare.main.repository.ClusterRepository;
import com.asquare.main.repository.ProjectRepository;
import com.asquare.main.repository.WingRepository;
import com.asquare.main.service.CustomerDashboardService;
import com.asquare.main.service.CustomerRegistrationService;
import com.asquare.main.service.RegistrationCaptchaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class PageController {

	private static final String CUSTOMER_ATTR = "customer";
	private static final String REGISTER_VIEW = "register";
	private final CustomerDashboardService customerDashboardService;
	private final BuilderRepository builderRepository;
	private final ProjectRepository projectRepository;
	private final ClusterRepository clusterRepository;
	private final WingRepository wingRepository;
	private final CustomerRegistrationService customerRegistrationService;
	private final RegistrationCaptchaService registrationCaptchaService;

	public PageController(CustomerDashboardService customerDashboardService,
			BuilderRepository builderRepository,
			ProjectRepository projectRepository,
			ClusterRepository clusterRepository,
			WingRepository wingRepository,
			CustomerRegistrationService customerRegistrationService,
			RegistrationCaptchaService registrationCaptchaService) {
		this.customerDashboardService = customerDashboardService;
		this.builderRepository = builderRepository;
		this.projectRepository = projectRepository;
		this.clusterRepository = clusterRepository;
		this.wingRepository = wingRepository;
		this.customerRegistrationService = customerRegistrationService;
		this.registrationCaptchaService = registrationCaptchaService;
	}

	@GetMapping({"/", "/index"})
	public String index() {
		log.debug("Serving index page");
		return "index";
	}

	@GetMapping("/pricing")
	public String pricing() {
		log.debug("Serving pricing page");
		return "pricing";
	}

	@GetMapping("/login")
	public String login() {
		log.debug("Serving login page");
		return "login";
	}

	@GetMapping("/register")
	public String register(Model model, HttpSession session) {
		prepareRegistrationModel(model, session);
		model.addAttribute(CUSTOMER_ATTR, new CustomerRegistrationRequest(null, null, null, null, null, null, null, null, null, null));
		log.debug("Serving registration page");
		return REGISTER_VIEW;
	}

	@GetMapping("/api/builders/{builderId}/projects")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<List<ProjectResponse>> getProjectsByBuilder(@PathVariable Long builderId) {
		List<Project> projects = projectRepository.findByBuilderBuilderIdAndActiveTrueOrderByProjectNameAsc(builderId);
		return ResponseEntity.ok(projects.stream()
			.map(p -> new ProjectResponse(p.getProjectId(), p.getProjectName(), p.getCity()))
			.toList());
	}

	@GetMapping("/api/projects/{projectId}/clusters")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<List<ClusterResponse>> getClustersByProject(@PathVariable Long projectId) {
		List<Cluster> clusters = clusterRepository.findByProjectProjectIdAndActiveTrueOrderByClusterNameAsc(projectId);
		return ResponseEntity.ok(clusters.stream()
			.map(c -> new ClusterResponse(c.getClusterId(), c.getClusterName()))
			.toList());
	}

	@GetMapping("/api/clusters/{clusterId}/wings")
	@ResponseBody
	@Transactional(readOnly = true)
	public ResponseEntity<List<WingResponse>> getWingsByCluster(@PathVariable Long clusterId) {
		List<Wing> wings = wingRepository.findByClusterClusterIdAndActiveTrueOrderByWingNameAsc(clusterId);
		return ResponseEntity.ok(wings.stream()
			.map(w -> new WingResponse(w.getWingId(), w.getWingName()))
			.toList());
	}

	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute(CUSTOMER_ATTR) CustomerRegistrationRequest request,
			@RequestParam(required = false) String captchaAnswer,
			@RequestParam(required = false) String website,
			HttpSession session,
			Model model) {
		if (!registrationCaptchaService.isValid(session, captchaAnswer)
				|| (website != null && !website.isBlank())) {
			prepareRegistrationModel(model, session);
			model.addAttribute("errorMessage", "Please answer the security question.");
			model.addAttribute(CUSTOMER_ATTR, request);
			return REGISTER_VIEW;
		}
		prepareRegistrationModel(model, session);
		try {
			customerRegistrationService.createCustomer(request);
			model.addAttribute(CUSTOMER_ATTR, new CustomerRegistrationRequest(null, null, null, null, null, null, null, null, null, null));
			model.addAttribute("successMessage", "Registration successful. Please sign in with your mobile number and login PIN.");
			return REGISTER_VIEW;
		} catch (IllegalArgumentException ex) {
			model.addAttribute("errorMessage", ex.getMessage());
			model.addAttribute(CUSTOMER_ATTR, request);
			return REGISTER_VIEW;
		}
	}

	private void prepareRegistrationModel(Model model, HttpSession session) {
		model.addAttribute("builders", builderRepository.findByActiveTrueOrderByBuilderNameAsc());
		if (session != null) {
			model.addAttribute("captchaQuestion", registrationCaptchaService.createQuestion(session));
		}
	}

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
		if (!(session.getAttribute("customerId") instanceof Long)) {
			log.warn("Blocked dashboard access without a customer session");
			return "redirect:/login?returnUrl=/dashboard";
		}
		Long customerId = (Long) session.getAttribute("customerId");
		model.addAttribute("currentBookings", customerDashboardService.getCurrentBookings(customerId));
		model.addAttribute("pastBookings", customerDashboardService.getPastBookings(customerId));
		log.debug("Serving customer dashboard for customerId={}", customerId);
		return "dashboard";
	}
}

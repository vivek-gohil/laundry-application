package com.asquare.main.service;

import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;

@Service
public class RegistrationCaptchaService {

    private static final String CAPTCHA_ANSWER_ATTRIBUTE = "registrationCaptchaAnswer";
    private final SecureRandom random = new SecureRandom();

    public String createQuestion(HttpSession session) {
        int firstNumber = random.nextInt(8) + 1;
        int secondNumber = random.nextInt(8) + 1;
        session.setAttribute(CAPTCHA_ANSWER_ATTRIBUTE, firstNumber + secondNumber);
        return firstNumber + " + " + secondNumber + " = ?";
    }

    public boolean isValid(HttpSession session, String answer) {
        Object expectedAnswer = session.getAttribute(CAPTCHA_ANSWER_ATTRIBUTE);
        session.removeAttribute(CAPTCHA_ANSWER_ATTRIBUTE);
        return expectedAnswer instanceof Integer expected
                && answer != null
                && answer.trim().matches("\\d{1,2}")
                && expected.equals(Integer.parseInt(answer.trim()));
    }
}

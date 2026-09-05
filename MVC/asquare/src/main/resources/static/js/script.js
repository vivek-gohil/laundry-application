// ============================================================
// GLOBAL ELEMENTS & CUSTOMER LOGIN STATUS
// ============================================================
// Hamburger menu button
const menu = document.querySelector('.menu-toggle');
// Navigation links container
const nav = document.querySelector('.nav-links');
// Check whether customer is marked as logged in
// sessionStorage stores data only for the current browser tab/session.
// Returns:
//   true  -> customerLoggedIn is "true"
//   false -> customerLoggedIn doesn't exist or has another value
const customerLoggedIn = () => sessionStorage.getItem('customerLoggedIn') === 'true';

function enableSelect(select, placeholder, options = []) {
  if (!select) return;
  select.disabled = false;
  const currentValue = select.value;
  select.innerHTML = `<option value="">${placeholder}</option>` + options.map((option) => `
    <option value="${option.value}">${option.label}</option>
  `).join('');
  if (options.some((option) => String(option.value) === String(currentValue))) {
    select.value = currentValue;
  }
}

function resetDependentSelects(selectList) {
  Array.from(document.querySelectorAll(selectList)).forEach((select) => {
    select.innerHTML = '<option value="">Select option</option>';
    select.disabled = true;
    select.value = '';
  });
}

const builderSelect = document.getElementById('builderId');
const projectSelect = document.getElementById('projectId');
const clusterSelect = document.getElementById('clusterId');
const wingSelect = document.getElementById('wingId');

if (builderSelect) {
  builderSelect.addEventListener('change', async () => {
    const builderId = builderSelect.value;
    if (!builderId) {
      resetDependentSelects('#projectId, #clusterId, #wingId');
      return;
    }
    projectSelect.innerHTML = '<option value="">Loading projects...</option>';
    projectSelect.disabled = true;
    clusterSelect.innerHTML = '<option value="">Select cluster</option>';
    clusterSelect.disabled = true;
    wingSelect.innerHTML = '<option value="">Select your wing</option>';
    wingSelect.disabled = true;

    try {
      const response = await fetch(`/api/builders/${builderId}/projects`);
      if (!response.ok) throw new Error('Unable to load projects.');
      const projects = await response.json();
      enableSelect(projectSelect, 'Select project', projects.map((project) => ({ value: project.projectId, label: project.projectName })));
      projectSelect.value = '';
    } catch (error) {
      console.error('Failed to load projects:', error);
      projectSelect.innerHTML = '<option value="">Unable to load projects</option>';
    }
  });
}

if (projectSelect) {
  projectSelect.addEventListener('change', async () => {
    const projectId = projectSelect.value;
    if (!projectId) {
      resetDependentSelects('#clusterId, #wingId');
      return;
    }
    clusterSelect.innerHTML = '<option value="">Loading clusters...</option>';
    clusterSelect.disabled = true;
    wingSelect.innerHTML = '<option value="">Select your wing</option>';
    wingSelect.disabled = true;

    try {
      const response = await fetch(`/api/projects/${projectId}/clusters`);
      if (!response.ok) throw new Error('Unable to load clusters.');
      const clusters = await response.json();
      enableSelect(clusterSelect, 'Select cluster', clusters.map((cluster) => ({ value: cluster.clusterId, label: cluster.clusterName })));
      clusterSelect.value = '';
    } catch (error) {
      console.error('Failed to load clusters:', error);
      clusterSelect.innerHTML = '<option value="">Unable to load clusters</option>';
    }
  });
}

if (clusterSelect) {
  clusterSelect.addEventListener('change', async () => {
    const clusterId = clusterSelect.value;
    if (!clusterId) {
      wingSelect.innerHTML = '<option value="">Select your wing</option>';
      wingSelect.disabled = true;
      return;
    }
    wingSelect.innerHTML = '<option value="">Loading wings...</option>';
    wingSelect.disabled = true;

    try {
      const response = await fetch(`/api/clusters/${clusterId}/wings`);
      if (!response.ok) throw new Error('Unable to load wings.');
      const wings = await response.json();
      enableSelect(wingSelect, 'Select your wing', wings.map((wing) => ({ value: wing.wingId, label: wing.wingName })));
      wingSelect.value = '';
    } catch (error) {
      console.error('Failed to load wings:', error);
      wingSelect.innerHTML = '<option value="">Unable to load wings</option>';
    }
  });
}

function getSafeReturnUrl(value) {
  return value?.startsWith('/') && !value.startsWith('//') ? value : '/';
}

function setMenuOpen(isOpen) {
  nav.classList.toggle('open', isOpen);
  menu.setAttribute('aria-expanded', String(isOpen));

  const pendingBooking = JSON.parse(sessionStorage.getItem('pendingBooking') || 'null');
  if (customerLoggedIn() && pendingBooking?.date && pickupDateInput && pickupForm) {
    pickupDateInput.value = pendingBooking.date;
    pickupForm.requestSubmit();
  }
  menu.setAttribute('aria-label', isOpen ? 'Close menu' : 'Open menu');
  menu.textContent = isOpen ? '\u00d7' : '\u2630';
}

menu.addEventListener('click', () => setMenuOpen(!nav.classList.contains('open')));
nav.addEventListener('click', (event) => {
  if (event.target.matches('a')) setMenuOpen(false);
});
document.addEventListener('keydown', (event) => {
  if (event.key === 'Escape') setMenuOpen(false);
});

const logoutButton = document.getElementById('logout-btn');
const customerMenu = document.querySelector('.customer-menu');
const customerMenuToggle = document.querySelector('.customer-menu-toggle');
if (customerMenu && customerMenuToggle) {
  customerMenuToggle.addEventListener('click', () => {
    const isOpen = customerMenu.classList.toggle('is-open');
    customerMenuToggle.setAttribute('aria-expanded', String(isOpen));
  });
  document.addEventListener('click', (event) => {
    if (!customerMenu.contains(event.target)) {
      customerMenu.classList.remove('is-open');
      customerMenuToggle.setAttribute('aria-expanded', 'false');
    }
  });
}
if (logoutButton) {
  logoutButton.addEventListener('click', async () => {
    logoutButton.disabled = true;
    try {
      const response = await fetch('/api/auth/logout', { method: 'POST' });
      if (!response.ok) throw new Error('Unable to log out.');
      sessionStorage.removeItem('customerLoggedIn');
      sessionStorage.removeItem('pendingBooking');
      window.location.href = '/';
    } catch (error) {
      logoutButton.disabled = false;
      window.alert(error.message);
    }
  });
}

const pickupForm = document.getElementById('pickup-form');
const pickupDateInput = document.getElementById('pickup-date');

if (pickupDateInput) {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  pickupDateInput.min = `${year}-${month}-${day}`;
}

if (pickupForm) {
  pickupForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const date = pickupDateInput?.value;
    const message = document.getElementById('form-message');
    const results = document.getElementById('pickup-results');

    if (!date) {
      message.textContent = 'Choose a pickup date to see available times.';
      results.hidden = true;
      return;
    }

    message.textContent = 'Checking pickup times...';
    results.hidden = true;

    try {
      const response = await fetch(`/api/pickup-slots?date=${encodeURIComponent(date)}`);
      const availability = await response.json();
      if (!response.ok) throw new Error(availability.message || 'Unable to load pickup times.');

      message.textContent = availability.message;
      results.innerHTML = availability.slots.length
        ? availability.slots.map((slot) => `
            <button class="pickup-slot${slot.available ? '' : ' is-full'}" type="button" data-slot-id="${slot.slotMasterId}" ${slot.available ? '' : 'disabled'}>
              <span>${slot.startTime} - ${slot.endTime}</span>
              <small>${slot.available ? `${slot.remainingCapacity} spaces left` : 'Fully booked'}</small>
            </button>`).join('')
        : '<p class="no-slots">No pickup slots are configured for this date.</p>';
      results.hidden = false;
      results.querySelectorAll('.pickup-slot:not(:disabled)').forEach((slotButton) => {
        slotButton.addEventListener('click', () => {
          bookPickup(date, slotButton.dataset.slotId, message, results);
        });
      });

      const pendingBooking = JSON.parse(sessionStorage.getItem('pendingBooking') || 'null');
      if (customerLoggedIn() && pendingBooking?.date === date) {
        const pendingSlot = results.querySelector(`[data-slot-id="${pendingBooking.slotMasterId}"]`);
        if (pendingSlot && !pendingSlot.disabled) {
          pendingSlot.click();
          sessionStorage.removeItem('pendingBooking');
        }
      }
    } catch (error) {
      message.textContent = error.message;
    }
  });

  const pendingBooking = JSON.parse(sessionStorage.getItem('pendingBooking') || 'null');
  if (customerLoggedIn() && pendingBooking?.date) {
    pickupDateInput.value = pendingBooking.date;
    pickupForm.requestSubmit();
  }
}

async function bookPickup(date, slotMasterId, message, results) {
  message.textContent = 'Booking your pickup...';

  try {
    const response = await fetch('/api/pickup-bookings', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ pickupDate: date, slotMasterId: Number(slotMasterId) })
    });
    const result = await response.json();

    if (response.status === 401) {
      sessionStorage.removeItem('customerLoggedIn');
      sessionStorage.setItem('pendingBooking', JSON.stringify({ date, slotMasterId }));
      window.location.href = `/login?returnUrl=${encodeURIComponent('/dashboard')}`;
      return;
    }
    if (!response.ok) throw new Error(result.message || 'Unable to book this pickup slot.');

    results.querySelectorAll('.pickup-slot').forEach((button) => button.classList.remove('is-selected'));
    const selectedSlot = results.querySelector(`[data-slot-id="${slotMasterId}"]`);
    if (selectedSlot) selectedSlot.classList.add('is-selected');
    message.textContent = result.message;
  } catch (error) {
    message.textContent = error.message;
  }
}

const loginForm = document.getElementById('login-form');
const phoneInput = document.getElementById('phone-number');
const loginPinInput = document.getElementById('login-pin');
const loginBtn = document.getElementById('login-btn');

if (loginForm) {
  loginForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const phone = phoneInput?.value.trim();
    const loginPin = loginPinInput?.value.trim();
    const message = document.getElementById('login-message');

    if (!phone || !/^\d{10}$/.test(phone)) {
      message.textContent = 'Enter a valid 10-digit mobile number first.';
      return;
    }
    if (!loginPin || !/^\d{4}$/.test(loginPin)) {
      message.textContent = 'Enter your 4-digit login PIN.';
      return;
    }

    loginBtn.disabled = true;
    message.textContent = 'Signing in...';
    try {
      const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ mobile: phone, loginPin })
      });
      const result = await response.json();
      if (!response.ok) throw new Error(result.message || 'Unable to sign in.');

      sessionStorage.setItem('customerLoggedIn', 'true');
      message.textContent = result.message;
      const requestedReturnUrl = new URLSearchParams(window.location.search).get('returnUrl');
      const returnUrl = requestedReturnUrl ? getSafeReturnUrl(requestedReturnUrl) : '/dashboard';
      window.location.href = returnUrl;
    } catch (error) {
      message.textContent = error.message;
      loginBtn.disabled = false;
    }
  });
}

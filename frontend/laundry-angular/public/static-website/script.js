const menu = document.querySelector('.menu-toggle');
const nav = document.querySelector('.nav-links');

function setMenuOpen(isOpen) {
  nav.classList.toggle('open', isOpen);
  menu.setAttribute('aria-expanded', String(isOpen));
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
  pickupForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const date = pickupDateInput?.value;
    document.getElementById('form-message').textContent = date
      ? 'Great — pickup times are available for your selected date.'
      : 'Choose a pickup date to see available times.';
  });
}

const loginForm = document.getElementById('login-form');
const sendOtpBtn = document.getElementById('send-otp-btn');
const phoneInput = document.getElementById('phone-number');
const otpWrapper = document.getElementById('otp-wrapper');
const otpInputs = otpWrapper ? Array.from(otpWrapper.querySelectorAll('.otp-digit')) : [];
const verifyBtn = document.getElementById('verify-btn');

function clearOtp() {
  otpInputs.forEach((input) => {
    input.value = '';
    input.disabled = true;
  });
}

function getOtpValue() {
  return otpInputs.map((input) => input.value.trim()).join('');
}

function setOtpVisibility(visible) {
  if (!otpWrapper) return;
  otpWrapper.style.display = visible ? 'flex' : 'none';
  otpInputs.forEach((input) => {
    input.disabled = !visible;
  });
}

if (sendOtpBtn && phoneInput && otpWrapper) {
  sendOtpBtn.addEventListener('click', () => {
    const phone = phoneInput.value.trim();
    const isValidPhone = /^[0-9]{10}$/.test(phone);

    if (!isValidPhone) {
      document.getElementById('login-message').textContent = 'Enter a valid 10-digit mobile number.';
      return;
    }

    setOtpVisibility(true);
    otpInputs.forEach((input) => {
      input.style.display = 'inline-block';
    });
    sendOtpBtn.style.display = 'none';
    if (verifyBtn) verifyBtn.style.display = 'inline-flex';
    if (otpInputs[0]) otpInputs[0].focus();
    document.getElementById('login-message').textContent = `OTP sent to ${phone}. Enter it to continue.`;
  });
}

otpInputs.forEach((input, index) => {
  input.addEventListener('input', (event) => {
    const value = event.target.value.replace(/[^0-9]/g, '');
    event.target.value = value;

    if (value.length === 1 && otpInputs[index + 1]) {
      otpInputs[index + 1].focus();
    }
  });

  input.addEventListener('keydown', (event) => {
    if (event.key === 'Backspace' && !event.target.value && otpInputs[index - 1]) {
      otpInputs[index - 1].focus();
    }
  });
});

if (loginForm) {
  loginForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const phone = phoneInput?.value.trim();
    const otp = getOtpValue();

    if (!phone || !/^[0-9]{10}$/.test(phone)) {
      document.getElementById('login-message').textContent = 'Enter a valid mobile number first.';
      return;
    }

    if (!otp || otp.length !== otpInputs.length) {
      document.getElementById('login-message').textContent = 'Enter the 4-digit OTP sent to your mobile.';
      return;
    }

    document.getElementById('login-message').textContent = 'Logged in successfully!';
  });
}

clearOtp();

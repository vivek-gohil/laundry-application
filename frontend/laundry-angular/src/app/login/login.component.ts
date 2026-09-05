import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  phone = '';
  otp = ['', '', '', ''];
  otpSent = false;
  message = '';
  menuOpen = false;

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }

  sendOtp(): void {
    if (!/^[0-9]{10}$/.test(this.phone)) {
      this.message = 'Enter a valid 10-digit mobile number.';
      return;
    }

    this.otpSent = true;
    this.otp = ['', '', '', ''];
    this.message = `OTP sent to ${this.phone}. Enter it to continue.`;
  }

  submitLogin(): void {
    if (!/^[0-9]{10}$/.test(this.phone)) {
      this.message = 'Enter a valid mobile number first.';
      return;
    }

    if (this.otp.join('').length !== 4) {
      this.message = 'Enter the 4-digit OTP sent to your mobile.';
      return;
    }

    this.message = 'Logged in successfully!';
  }

  moveFocus(event: Event, index: number): void {
    const input = event.target as HTMLInputElement;
    const value = input.value.replace(/[^0-9]/g, '');
    input.value = value;

    if (value && index < this.otp.length - 1) {
      const next = input.nextElementSibling as HTMLInputElement | null;
      next?.focus();
    }
  }

  moveBack(event: KeyboardEvent, index: number): void {
    const input = event.target as HTMLInputElement;
    if (event.key === 'Backspace' && !input.value && index > 0) {
      const previous = input.previousElementSibling as HTMLInputElement | null;
      previous?.focus();
    }
  }
}

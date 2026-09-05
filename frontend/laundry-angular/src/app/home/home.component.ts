import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './home.component.html'
})
export class HomeComponent {
  service = 'Professional ironing';
  pickupDate = '';
  formMessage = '';
  minDate = '';
  menuOpen = false;

  constructor() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    this.minDate = `${year}-${month}-${day}`;
  }

  submitPickup(): void {
    this.formMessage = this.pickupDate
      ? 'Great — pickup times are available for your selected date.'
      : 'Choose a pickup date to see available times.';
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }
}

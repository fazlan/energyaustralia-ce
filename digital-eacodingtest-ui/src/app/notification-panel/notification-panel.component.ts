import { Component, Input } from '@angular/core';

@Component({
  selector: 'eact-notification-panel',
  template: `
  <div class="notification-panel" *ngIf="notification">
    <span class="notification {{type}}">{{notification}}</span>
  </div>
  `
})
export class NotificationPanelComponent {
  @Input() notification: string;
  @Input() type: string;
}

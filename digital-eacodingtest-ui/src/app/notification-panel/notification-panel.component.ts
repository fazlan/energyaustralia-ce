import { Component, Input } from '@angular/core';

@Component({
  selector: 'eact-notification-panel',
  template: `
  <div class="notification-panel">
    <div class="notification {{type}}">
       <ng-content></ng-content>
    </div>
  </div>
  `
})
export class NotificationPanelComponent {
  @Input() type: string;
}

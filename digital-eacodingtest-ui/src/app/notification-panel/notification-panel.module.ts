import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationPanelComponent } from './notification-panel.component';

@NgModule({
  declarations: [NotificationPanelComponent],
  imports: [
    CommonModule
  ],
  exports: [NotificationPanelComponent]
})
export class NotificationPanelModule {
}

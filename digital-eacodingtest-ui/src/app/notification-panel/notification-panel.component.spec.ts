import { TestBed } from '@angular/core/testing';
import { Component } from '@angular/core';

import { NotificationPanelComponent } from './notification-panel.component';

@Component({
  selector: 'eact-notification-panel-test',
  template: `<eact-notification-panel [type]="'success'">Test content</eact-notification-panel>`
})
class NotificationPanelTestComponent {
}

describe('NotificationPanelComponent', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        NotificationPanelComponent,
        NotificationPanelTestComponent
      ],
    }).compileComponents();
  });

  it('should create the notification component', () => {
    const fixture = TestBed.createComponent(NotificationPanelComponent);
    const component = fixture.debugElement.componentInstance;

    expect(component).toBeTruthy();
  });

  it(`should render notification type with content passed`, () => {
    const fixture = TestBed.createComponent(NotificationPanelTestComponent);

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector(`.notification.success`).textContent).toContain('Test content');
  });

});

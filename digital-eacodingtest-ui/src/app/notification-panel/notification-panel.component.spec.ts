import { TestBed } from '@angular/core/testing';

import { NotificationPanelComponent } from './notification-panel.component';

describe('NotificationPanelComponent', () => {

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        NotificationPanelComponent
      ],
    }).compileComponents();
  });

  it('should create the notification component', () => {
    const fixture = TestBed.createComponent(NotificationPanelComponent);
    const component = fixture.debugElement.componentInstance;

    expect(component).toBeTruthy();
  });

  it(`should render notification as passed via 'notification' input`, () => {
    const fixture = TestBed.createComponent(NotificationPanelComponent);
    const component = fixture.debugElement.componentInstance;
    component.notification = 'Notification message';

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.notification').textContent).toContain(component.notification);
  });

  it(`should render notification type as passed via 'type' input`, () => {
    const fixture = TestBed.createComponent(NotificationPanelComponent);
    const component = fixture.debugElement.componentInstance;
    component.notification = 'Notification message';
    component.type = 'success';

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector(`.notification.${component.type}`).textContent).toContain(component.notification);
  });

});

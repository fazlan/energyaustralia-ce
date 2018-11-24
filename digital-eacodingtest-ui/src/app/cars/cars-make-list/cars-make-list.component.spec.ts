import { TestBed } from '@angular/core/testing';
import { Component, Input, Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { CarMake } from './../car-make.model';
import { CarsService } from './../cars.service';
import { CarsMakeListComponent } from './cars-make-list.component';

@Component({
  selector: 'eact-notification-panel',
  template: `<ng-content></ng-content> ({{type}})`
})
class MockedNotificationPanelComponent {
  @Input() type: string;
}

@Component({
  selector: 'eact-cars-make',
  template: ``
})
class MockedCarsShowComponent {
  @Input() car: CarMake;
}

@Injectable()
class MockedCarsService {
  listCarsByMake(): Observable<CarMake[]> {
    return of([]);
  }
}

describe('CarsMakeListComponent', () => {
  const response: CarMake[] = [{ make: 'Some make', shows: [] }];

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        CarsMakeListComponent,
        MockedNotificationPanelComponent,
        MockedCarsShowComponent
      ],
      providers: [
        { provide: CarsService, useClass: MockedCarsService }
      ]
    })
      .compileComponents();
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);
    const component = fixture.debugElement.componentInstance;

    expect(component).toBeTruthy();
  });

  it('should subscribe to the cars observable', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);
    const component = fixture.debugElement.componentInstance;
    const carsService: CarsService = TestBed.get(CarsService);
    spyOn(carsService, 'listCarsByMake').and.returnValue(of(response));

    fixture.detectChanges();

    expect(component.cars).toEqual(response);
    expect(carsService.listCarsByMake).toHaveBeenCalled();
  });

  it('should render warning notification when no cars returned', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('eact-notification-panel').textContent).toEqual('No results found, please refresh. (warn)');
  });

  it('should render success notification when single car returned', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);
    const carsService: CarsService = TestBed.get(CarsService);
    spyOn(carsService, 'listCarsByMake').and.returnValue(of(response));

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('eact-notification-panel').textContent).toEqual('1 car found (success)');
  });

  it('should render success notification when multiple cars returned', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);
    const carsService: CarsService = TestBed.get(CarsService);
    spyOn(carsService, 'listCarsByMake').and.returnValue(of([...response, ...response]));

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('eact-notification-panel').textContent).toEqual('2 cars found (success)');
  });

  it('should render all the shows the make was displayed', () => {
    const fixture = TestBed.createComponent(CarsMakeListComponent);
    const carsService: CarsService = TestBed.get(CarsService);
    spyOn(carsService, 'listCarsByMake').and.returnValue(of([...response, ...response]));

    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelectorAll('eact-cars-make').length).toEqual(2);

  });
});

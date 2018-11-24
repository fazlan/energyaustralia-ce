import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { CarsService } from './../cars.service';
import { CarMake } from './../car-make.model';

@Component({
  selector: 'eact-cars-make-list',
  template: `
     <eact-notification-panel *ngIf="cars && cars.length === 0" [type]="'warn'">
        <span>No results found, please <a (click)="onRefresh($event)">refresh</a>.</span>
     </eact-notification-panel>
     <eact-notification-panel *ngIf="cars && cars.length > 0" [type]="'success'">
        <span>{{cars.length | i18nPlural: {'=1': '1 car', 'other': '# cars'} }} found</span>
     </eact-notification-panel>
     <eact-cars-make [car]="car" *ngFor="let car of cars"></eact-cars-make>
  `
})
export class CarsMakeListComponent implements OnInit, OnDestroy {

  destroy$: Subject<void> = new Subject<void>();
  cars: CarMake[];
  title = 'Cars and shows by make';

  constructor(private carsService: CarsService) {
  }

  ngOnInit(): void {
    this.load();
  }

  onRefresh($event): void {
    $event.preventDefault();
    this.load();
  }

  private load(): void {
    this.carsService.listCarsByMake()
      .pipe(takeUntil(this.destroy$))
      .subscribe(cars => this.cars = cars);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}

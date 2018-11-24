import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { CarsService } from './../cars.service';
import { CarMake } from './../car-make.model';

@Component({
  selector: 'eact-cars-make-list',
  template: `
     <h1>{{title}}</h1>
     <button (click)="onTitleChange($event)">Change title</button>
     <eact-notification-panel *ngIf="cars && cars.length === 0" [notification]="'No results found, please refresh.'" [type]="'warn'"></eact-notification-panel>
     <eact-notification-panel *ngIf="cars && cars.length > 0" [notification]="cars.length + ' results found.'" [type]="'success'"></eact-notification-panel>
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
    this.carsService.listCarsByMake()
      .pipe(takeUntil(this.destroy$))
      .subscribe(cars => this.cars = cars);
  }

  onTitleChange($event): void  {
    $event.preventDefault();
    this.title = 'Cars and shows by make updated';
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}

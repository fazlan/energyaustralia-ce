import { Component, Input } from '@angular/core';

import { CarMake } from './../car-make.model';

@Component({
  selector: 'eact-cars-make',
  template: `
     <div class="cars-make-panel">
        <span class="make-name">{{car.make}} ({{car.shows.length | i18nPlural: {'=0': 'No shows', '=1': '1 show', 'other': '# shows'} }})</span>
        <eact-cars-show [show]="show" *ngFor="let show of car.shows"></eact-cars-show>
     </div>
  `
})
export class CarsMakeComponent {
  @Input() car: CarMake;
}

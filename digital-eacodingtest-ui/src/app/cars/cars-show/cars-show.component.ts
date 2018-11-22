import { Component, Input } from '@angular/core';

import { CarShow } from './../car-show.model';

@Component({
  selector: 'eact-cars-show',
  template: `
  <div class="cars-show-panel">
    <span class="show-name">- {{show.name}} ({{show.models.length| i18nPlural: {'=1': '1 model', 'other': '# models'} }})</span>
    <eact-cars-models [models]="show.models"></eact-cars-models>
  </div>
  `
})
export class CarsShowComponent {
  @Input() show: CarShow;
}

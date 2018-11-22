import { Component, Input } from '@angular/core';

@Component({
  selector: 'eact-cars-models',
  template: `
  <ul class="model-list">
    <li *ngFor="let model of models"> - {{model}}</li>
  </ul>
  `
})
export class CarsModelsComponent {
  @Input() models: Array<string>;
}

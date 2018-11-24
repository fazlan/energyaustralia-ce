import { Component, Input } from '@angular/core';

let count = 1;
@Component({
  selector: 'eact-cars-models',
  template: `
  <ul class="model-list">
    <li *ngFor="let model of models"> - {{getModel(model)}}</li>
  </ul>
  `
})
export class CarsModelsComponent {
  @Input() models: Array<string>;

  getModel(model: string): string {
    console.log(`${count++}. getModel called ${model}`);
    return model;
  }
}

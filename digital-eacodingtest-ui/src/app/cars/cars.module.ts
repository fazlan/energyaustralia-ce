import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { NotificationPanelModule } from './../notification-panel/notification-panel.module';
import { CarsRoutingModule } from './cars-routing.module';
import { CarsService } from './cars.service';
import { CarsMakeComponent } from './cars-make/cars-make.component';
import { CarsMakeListComponent } from './cars-make-list/cars-make-list.component';
import { CarsModelsComponent } from './cars-models/cars-models.component';
import { CarsShowComponent } from './cars-show/cars-show.component';

@NgModule({
  declarations: [
    CarsMakeComponent,
    CarsMakeListComponent,
    CarsModelsComponent,
    CarsShowComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    NotificationPanelModule,
    NotificationPanelModule,
    CarsRoutingModule
  ],
  providers: [CarsService]
})
export class CarsModule {
}

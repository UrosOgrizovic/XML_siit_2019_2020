import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { CoreModule } from './core/core.module';
import { HomeModule } from './home/home.module';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from './shared/shared.module';
import { SciencepapersModule } from './sciencepapers/sciencepapers.module';

@NgModule({
  declarations: [
    AppComponent,    
  ],
  imports: [
    AppRoutingModule,
    HttpModule,
    SharedModule,
    HomeModule,
    SciencepapersModule,
    FormsModule,
    HttpClientModule,
    CoreModule,
    HomeModule,
    BrowserModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

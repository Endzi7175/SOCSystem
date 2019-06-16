import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SearchTableComponent } from './search-table/search-table.component';
import { MaterialModule} from './material/material.module';

import { MatRippleModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchTableComponent,
    
    
   
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    MatFormFieldModule,
    MatRippleModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

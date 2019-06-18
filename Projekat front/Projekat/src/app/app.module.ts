import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule} from '@angular/material/table';
import { MatPaginatorModule} from '@angular/material/paginator';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SearchTableComponent } from './search-table/search-table.component';
import { AlarmTableComponent } from './alarm-table/alarm-table.component';
import {  MatFormFieldModule } from '@angular/material/form-field';
import { HttpClientModule } from '@angular/common/http';
import { RuleAdderComponent } from './rule-adder/rule-adder.component';
import { MatInputModule } from '@angular/material/input';
import { ConditionComponent } from './condition/condition.component';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchTableComponent,
    AlarmTableComponent,
    RuleAdderComponent,
    ConditionComponent

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    HttpClientModule,
    MatInputModule,
    MatSelectModule,
    FormsModule,
    MatButtonModule
  ],
  entryComponents: [ConditionComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { FormService } from './form.service';
import { saveAs } from 'file-saver';
import { AuthService } from '../authentication/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router'

@Component({
  selector: 'app-form-card',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
})
export class FormComponent implements OnInit {
  
  filenames: string[] = [];
  fileStatus = { status: '', requestType: '', percent: 0 };
  public form!: FormGroup;
  public formSubmitted: boolean = false;
  
  // A map to keep track of field names and their corresponding files
  private selectedFiles: Map<string, File> = new Map();

  workExperience: String = '';

  constructor(private fb: FormBuilder, private formService: FormService,private router: Router, private authService: AuthService, private _snackBar: MatSnackBar) {}

  async ngOnInit(): Promise<void> {

    // Checking if the user is logged in
    const loggedIn = await this.authService.isLoggedIn();

    if (loggedIn) {
      // Do something specific for logged-in users
      console.log('User is logged in.');
      // Note: You can add more functionality here depending on what you want to do for logged-in users.
    } else {
      console.log('User is not logged in.');
    }
    
    this.form = this.fb.group({
      personalInfo: this.createPersonalInfoFormGroup(),
      addressInfo: this.createAddressInfoFormGroup(),
      nationalIdentity: this.createNationalIdentityFormGroup(),
      dependents: this.fb.array([]),
      emergencyContacts: this.fb.array([]),
      recognitionAcknowledged: new FormControl(false),
    });

    this.addDependent();
    this.addEmergencyContact();
  }

    // define a function to upload files
    onUploadFile(event: Event, fieldName: string): void {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files[0]) {
        const file = input.files[0];
        // Example: File size validation (2MB)
        if (file.size > 2 * 1024 * 1024) {
          console.error('File size should be less than 2MB');
          return;
        }
    
        // If file size is valid, set the form field and upload the file
        this.form.get(fieldName)?.setValue(file);
        this.selectedFiles.set(fieldName, file);  // Add/Update the file in the map
      }
    }
    

      // define a function to download files
      onDownloadFile(attachmentId: number): void {
        this.formService.download(attachmentId).subscribe(
            event => {
                console.log(event);
                this.reportProgress(event);
            },
            (error: HttpErrorResponse) => {
                console.log(error);
            }
        );
    }
    
      

    private reportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
      switch(httpEvent.type) {
        case HttpEventType.UploadProgress:
          this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Uploading... ');
          break;
        case HttpEventType.DownloadProgress:
          this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Downloading... ');
          break;
        case HttpEventType.ResponseHeader:
          console.log('Header returned', httpEvent);
          break;
        case HttpEventType.Response:
          if (httpEvent.body instanceof Array) {
            this.fileStatus.status = 'done';
            for (const filename of httpEvent.body) {
              this.filenames.unshift(filename);
            }
          } else {
            saveAs(new File([httpEvent.body!], httpEvent.headers.get('File-Name')!, 
                    {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));
            // saveAs(new Blob([httpEvent.body!], 
            //   { type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}),
            //    httpEvent.headers.get('File-Name'));
          }
          this.fileStatus.status = 'done';
          break;
          default:
            console.log(httpEvent);
            break;
        
      }
    }

    private updateStatus(loaded: number, total: number, requestType: string): void {
      this.fileStatus.status = 'progress';
      this.fileStatus.requestType = requestType;
      this.fileStatus.percent = Math.round(100 * loaded / total);
    }
  

  createPersonalInfoFormGroup(): FormGroup {
    return this.fb.group({
      fullName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      nationality: ['', [Validators.required, Validators.pattern('[a-zA-Z ]+')]],
      gender: ['', Validators.required],
      bloodType: ['', Validators.required],
      martialStatus: ['', Validators.required],
      //workExperience: ['', Validators.required],
      
    });
  }

  createAddressInfoFormGroup(): FormGroup {
    return this.fb.group({
      addressLine: ['', Validators.required],
      country: ['', Validators.required],
      city: ['', Validators.required],
      zipCode: ['', Validators.required],
      additionalCode: [''],
      mobileNumber: [
        '',
        [Validators.required, Validators.pattern('^([0-9]{13})$')],
      ], // 13 digits including coutry code
      emailAddress: ['', [Validators.required, Validators.email]],
    });
  }

  createNationalIdentityFormGroup(): FormGroup {
    return this.fb.group({
      idNumber: ['', Validators.required],
      expiryDate: ['', Validators.required],
      placeOfIssue: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      placeOfBirth: ['', Validators.required],
    });
  }

  get dependents(): FormArray {
    return this.form.get('dependents') as FormArray;
  }

  get emergencyContacts(): FormArray {
    return this.form.get('emergencyContacts') as FormArray;
  }

  addDependent(): void {
    this.dependents.push(
      this.fb.group({
        name: ['', Validators.required],
        kinship: ['', Validators.required],
        dateOfBirth: ['', Validators.required],
      })
    );
  }

  addEmergencyContact(): void {
    this.emergencyContacts.push(
      this.fb.group({
        name: ['', Validators.required],
        kinship: ['', Validators.required],
        phoneNumber: ['', [Validators.required, Validators.pattern('^([0-9]{13})$')],] // 13 digits includig coountry code
      })
    );
  }

  removeDependent(index: number): void {
    this.dependents.removeAt(index);
  }

  removeEmergencyContact(index: number): void {
    this.emergencyContacts.removeAt(index);
  }

 // Function to submit the form
 onSubmit() {
  if (this.form) {
    const formData = new FormData();

    // Append form data
    formData.append('applicant', JSON.stringify(this.form.get('personalInfo')?.value));
    formData.append('address', JSON.stringify(this.form.get('addressInfo')?.value));
    formData.append('nationalIdentity', JSON.stringify(this.form.get('nationalIdentity')?.value));
    formData.append('emergencyContacts', JSON.stringify(this.form.get('emergencyContacts')?.value));
    formData.append('dependencies', JSON.stringify(this.form.get('dependents')?.value));

    // Append files from the map
    this.selectedFiles.forEach((file) => {
      formData.append('attachments', file, file.name);
    });

    // Send the form data
    this.formService.submitForm(formData).subscribe(
      (response: any) => {
        console.log(response.message);
        this.formSubmitted = true;

        // Redirect to the success page upon successful form submission
        this.router.navigate(['/applicant/submit']);
      },
      (error) => {
        console.log('An error occurred', error);
        this.openSnackBar("Please, complete the missing fields!");  // Keep the snackbar for error notifications
      }
    );
  }
}

openSnackBar(message: string) {
  this._snackBar.open(message, "", {
    duration: 2000,
    panelClass: ['style-msg']
  });
}


showWorkExperienceUploads(event: any) {
  this.workExperience = event.target.value;
}


}
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicantService } from '../dashboard/applicant.service';
import { FormService } from '../form/form.service';
import { KeycloakService } from 'keycloak-angular';
import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { saveAs } from 'file-saver';

@Component({
  selector: 'app-applicant-details',
  templateUrl: './applicant-details.component.html',
  styleUrls: ['./applicant-details.component.css']
})
export class ApplicantDetailsComponent implements OnInit {
  applicantId: number = 0;
  applicant: any;
  address: any;
  attachments: any[] = [];
  dependencies: any[] = [];
  emergencyContacts: any[] = [];
  nationalIdentity: any;

  public firstName: string = '';
  public lastName: string = '';

  filenames: string[] = [];
  fileStatus = { status: '', requestType: '', percent: 0 };

  currentDownloadingFileName: string = ''; // <-- Added this to store the filename before download



  // Inject the ApplicantService into the component's constructor
  constructor(
    private route: ActivatedRoute,
    private applicantService: ApplicantService,
    private keycloakService: KeycloakService,
    private formService: FormService
  ) { }

  ngOnInit(): void {
    // Retrieve the applicantId from the route (suppose it's passed as a parameter)
    const id = this.route.snapshot.paramMap.get('id');
    this.applicantId = id ? +id : 0;  // Fallback to 0 or another default value if id is null
    
    // Fetch the applicant details using the service
    this.getApplicantDetails(this.applicantId);
    this.getUserDetails();
  }

  private getUserDetails(): void {
    const userDetails = this.keycloakService.getKeycloakInstance().tokenParsed;
    if (userDetails) {
      this.firstName = userDetails['given_name'] || ''; // 'given_name' usually contains the first name in Keycloak.
      this.lastName = userDetails['family_name'] || ''; // 'family_name' usually contains the last name in Keycloak.
    }
}

    // List of fixed attachment types
    attachmentTypes: string[] = [
      'CV',
      'Previous Experience Letters',
      'Educational Certificates',
      'Personal Photo',
      'National ID',
      'National Address',
      'Bank Account',
      'Family Card'
  ];

  getAttachmentType(index: number): string {
      return this.attachmentTypes[index] || 'Unknown';
  }

  getApplicantDetails(id: number): void {
    this.applicantService.getApplicant(id).subscribe(
      data => {
        console.log(data);
        this.applicant = data;
        this.address = data.address;
        this.attachments = data.attachments;
        this.dependencies = data.dependencies;
        this.emergencyContacts = data.emergencyContacts;
        this.nationalIdentity = data.nationalIdentity;
      },
      error => {
        console.error('There was an error fetching the data:', error);
      }
    );
  }

  onDownloadFile(attachmentId: number, attachmentName: string): void {
    this.currentDownloadingFileName = attachmentName; // Set the filename before downloading
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
            const downloadedFileName = httpEvent.headers.get('File-Name') || this.currentDownloadingFileName || 'default-filename.ext'; // <-- Modified this line to use the currentDownloadingFileName as a fallback
            saveAs(new File([httpEvent.body!], downloadedFileName, 
                    {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));
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
  logout(): void {
    this.keycloakService.logout('http://localhost:4200');  // Replace with your desired redirect URL after logout.
  }
  
}

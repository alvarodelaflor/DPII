use `Sample`;

revoke all privileges on `Sample`.* from 'acme-user'@'%';

revoke all privileges on `Sample`.* from 'acme-manager'@'%';
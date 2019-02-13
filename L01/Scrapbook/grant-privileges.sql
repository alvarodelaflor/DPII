grant select, insert, update, delete 
	on `Sample`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Sample`.* to 'acme-manager'@'%';

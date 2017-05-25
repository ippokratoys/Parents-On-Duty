use test3;

INSERT INTO login (email, pwd)
values('makisdelaportas@gmail.com', 'saidisgrandmother');

insert into organiser(Login_email, Police_id, Name, Surname, Birthdate)
values('makisdelaportas@gmail.com', 'ICECUBE', 'Makis', 'Delaportas', '2017-05-06');
 
insert into eventsgroup(id_events_group, Name, Type, organiser_login_email,description)
values(15, 'That kid from the block', 'Education', 'makisdelaportas@gmail.com',"Straight out from the underground ;) psakstw" );
 
insert into locationowner(login_email, name, surname)
values('makisdelaportas@gmail.com', 'Makes', 'Delaportas');
 
insert into location(id, address, postcode, name, locationowner_login_email)
values(3, 'Alexi Tsipra 420, Galatsi', '12453', 'Fix', 'makisdelaportas@gmail.com');
 
insert into events(id_events, day, time, location_id, organiser_login_email, eventsgroup_id_events_group,price)
values(1, 'Sunday', '16:20', 3, 'makisdelaportas@gmail.com', 15, 4.20);

insert into eventsfeedback(events_id_events, id_events_feedback,content)
values(1,1,"It was awesome");
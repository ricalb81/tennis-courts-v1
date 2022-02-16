insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier'),
(2, 'Arthur Ashe Stadium'),
(3, 'Indian Wells Tennis Garden');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1),
        (2, '2022-02-14 10:00:00.00', '2022-02-14 11:00:00.00', 1),
        (3, '2022-02-14 12:00:00.00', '2022-02-14 13:00:00.00', 2),
        (4, '2022-02-14 14:00:00.00', '2022-02-14 15:00:00.00', 1),
        (5, '2022-02-14 15:00:00.00', '2022-02-14 16:00:00.00', 3),
        (6, '2022-02-14 20:00:00.00', '2022-02-14 21:00:00.00', 3);

    insert into reservation(ID , DATE_CREATE , DATE_UPDATE , IP_NUMBER_CREATE, IP_NUMBER_UPDATE ,  user_create, user_update, refund_value, reservation_status, amount, GUEST_ID , schedule_id)
        values(1, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 1, 10.00, 1, 1),
        (2, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 0, 10.00, 1, 1),
        (3, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 0, 10.00, 1, 1),
        (4, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 0, 10.00, 1, 3),
        (5, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 1, 10.00, 1, 5),
        (6, '2022-02-11 19:36:49.877', '2022-02-11 19:36:49.877', null, null, 1, 1, 7.50, 2, 10.00, 1, 6);
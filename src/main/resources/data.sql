INSERT INTO users(id, email, role)
VALUES (1, 'example1@gmail.com', 0),
       (2, 'example2@gmail.com', 1),
       (3, 'example3@gmail.com', 0),
       (4, 'example4@gmail.com', 1),
       (5, 'coordinator1@gmail.com', 2);
alter sequence users_id_seq restart with 6;

INSERT INTO candidates(id, user_id)
VALUES (1, 1),
       (2, 3);
alter sequence candidates_id_seq restart with 3;

INSERT INTO candidate_slots(id, date_from, date_to, candidate_id)
VALUES (1, '2022-12-22 09:00:00',
        '2022-12-22 17:00:00', 1),
       (2, '2022-12-23 09:00:00',
        '2022-12-23 20:00:00', 2),
       (3, '2022-12-25 09:00:00',
        '2022-12-25 21:00:00', 2);
alter sequence candidate_slots_id_seq restart with 4;

INSERT INTO interviewers(id, booking_limit, user_id)
VALUES (1, 5, 2),
       (2, 3, 4);
alter sequence interviewers_id_seq restart with 3;

INSERT INTO interviewer_slots(id, day_of_week, t_from, t_to, week_num, interviewer_id)

VALUES (1, 4, '9:00', '18:00', 202251, 1),
       (2, 4, '8:00', '19:30', 202251, 2),
       (3, 5, '8:00', '19:30', 202251, 2),
       (4, 6, '10:00', '16:30', 202251, 1),
       (5, 7, '09:00', '21:00', 202251, 1);
alter sequence interviewer_slots_id_seq restart with 6;

INSERT INTO bookings(id, description, t_from, status, subject, t_to, candidate_slots_id,
                     interviewer_slots_id)
VALUES (1, 'description', '2022-12-22 09:00:00', 1, 'subject',
        '2022-12-22 10:30:00', 1, 1),
       (2, 'description', '2022-12-22 11:00:00', 1, 'subject',
        '2022-12-22 12:30:00', 1, 2),
       (3, 'description', '2022-12-23 11:00:00', 1, 'subject',
        '2022-12-23 12:30:00', 2, 3);
alter sequence bookings_id_seq restart with 4;

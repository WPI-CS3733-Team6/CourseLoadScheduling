-- 7) Account Information 
-- Shows the user's username, first and last name, email and/or secondary email,
-- phone number (optional), and required minimum courses (if they have filled their 
-- requirement, it will show up as 0)

SELECT
users.user_name,
users.first_name,
users.last_name,
users.email,
users.phone_num,
users.secondary_email,
ins.req_courses,
(ins.req_courses - registered_req_courses) AS remaining

FROM users
LEFT OUTER JOIN instructors ins
ON users.id = ins.user_id
LEFT OUTER JOIN
(SELECT COUNT(reg.instructor_id) AS registered_req_courses
 FROM instructors ins, instructor_course_link_registered reg
 WHERE ins.user_id = reg.instructor_id AND NOT reg.deleted) AS registered_req_courses
 -- Needs testing to see if the check for deleted works or not
ON users.id = ins.user_id
WHERE users.id = :userId
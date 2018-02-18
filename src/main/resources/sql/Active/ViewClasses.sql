-- 2) View Classes
-- Displays detailed class information to the user for the
-- classes they have been linked for (registered apporved). 
SELECT
se.expected_pop,
sc.meeting_days,
ci.course_num, ci.course_name

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LET JOIN course_instance cinst
ON se.instance_id = cinst.id
LEFT JOIN course_information ci
ON cinst.course_id = ci.id
LEFT JOIN instructor_course_link_registered cart
ON cinst.id = cart.instance_id
WHERE cinst.id
IN(
  SELECT instance_id
  FROM instructor_course_link_registered
  WHERE instructor_id
  IN(
    SELECT id
    FROM instructors
    WHERE user_id = :userId
  )
)
ORDER BY se.id ASC;
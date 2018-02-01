-- 2) View Classes
-- Displays detailed class information to the user for the
-- classes they have been linked for (registered apporved). 
SELECT
se.expected_pop,
sc.meeting_days,
ci.course_num, ci.course_name,
se.deleted

FROM course_sections se
LEFT JOIN course_schedule sc
ON se.id = sc.section_id
LEFT JOIN course_information ci
ON se.course_num = ci.course_num
LEFT JOIN instructor_course_link_registered cart
ON se.id = cart.section_id
WHERE se.id
IN(
  SELECT section_id
  FROM instructor_course_link_registered
  WHERE instructor_id
  IN(
    SELECT id
    FROM instructors
    WHERE user_id = ?
  )
)
ORDER BY se.id ASC;

-- 5) Registration Cart
-- Displays the name of the courses in the cart that have a status of 
-- 'registered'.
SELECT
ci.course_name

FROM course_information ci
WHERE ci.course_num = (
  SELECT cs.course_num
  FROM course_sections cs
  WHERE cs.section_num = (
    SELECT ic.section_id
    FROM instructor_course_link_cart ic
    WHERE ic.instructor_id = ?
  )
)
ORDER BY ci.course_name ASC;

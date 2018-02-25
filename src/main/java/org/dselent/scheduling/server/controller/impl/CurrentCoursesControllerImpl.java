package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.CurrentCoursesController;
import org.dselent.scheduling.server.dto.CourseInstanceDto;
import org.dselent.scheduling.server.dto.CourseScheduleDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.CurrentCourses;
import org.dselent.scheduling.server.requests.DetailedSchedule;
import org.dselent.scheduling.server.service.CurrentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CurrentCoursesControllerImpl implements CurrentCoursesController{
	
	@Autowired	
	private CurrentCoursesService currentCoursesService;
	
	@Override
	public ResponseEntity<String> currentCourses (@RequestBody Map<String,String> request) throws Exception {
		
		String response = "";
		
		Integer userId = Integer.parseInt(request.get(CurrentCourses.getBodyName(CurrentCourses.BodyKey.USER_ID)));
		
		ArrayList<CourseInstanceDto> instanceList = currentCoursesService.currentCourses(userId);
		ArrayList<Integer> courseId = new ArrayList<Integer>();
		ArrayList<Integer> id = new ArrayList<Integer>();
		ArrayList<Integer> sectionNo = new ArrayList<Integer>();
		ArrayList<String> term = new ArrayList<String>();
		
		for(int i = 0; i < instanceList.size(); i++) {
			CourseInstanceDto instance = instanceList.get(i);
			courseId.add(instance.getCourse_id());
			id.add(instance.getId());
			sectionNo.add(instance.getSectionNo());
			term.add(instance.getTerm());
		}
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", id);
		keys.put("courseId", courseId);
		keys.put("term", term);
		keys.put("sectionNum", sectionNo);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> detailedSchedule(@RequestBody Map<String, String> request) throws Exception {
		
		String response = "";
		
		Integer userId = Integer.parseInt(request.get(DetailedSchedule.getBodyName(DetailedSchedule.BodyKey.USER_ID)));
		
		
		List<CourseScheduleDto> currentCoursesList = currentCoursesService.detailedSchedule(userId);
		ArrayList<Integer> id = new ArrayList<Integer>();
		ArrayList<String> lectureType = new ArrayList<String>();
		ArrayList<String> meetingDays = new ArrayList<String>();
		ArrayList<Integer> sectionId = new ArrayList<Integer>();
		ArrayList<Integer> timeEnd = new ArrayList<Integer>();
		ArrayList<Integer> timeStart = new ArrayList<Integer>();
		
		for(int i = 0; i < currentCoursesList.size(); i++) {
			CourseScheduleDto schedule = currentCoursesList.get(i);
			id.add(schedule.getId());
			lectureType.add(schedule.getLecture_type());
			meetingDays.add(schedule.getMeeting_days());
			sectionId.add(schedule.getSection_id());
			timeEnd.add(schedule.getTime_end());
			timeStart.add(schedule.getTime_start());
		}
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", id);
		keys.put("lectureType", lectureType);
		keys.put("meetingDays", meetingDays);
		keys.put("sectionId", sectionId);
		keys.put("timeEnd", timeEnd);
		keys.put("timeStart", timeStart);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keys);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}

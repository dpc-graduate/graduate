package cn.sjxy.graduate;

import cn.sjxy.graduate.dao.RestaurantDao;
import cn.sjxy.graduate.entity.Comment;
import cn.sjxy.graduate.entity.Member;
import cn.sjxy.graduate.entity.News;
import cn.sjxy.graduate.entity.Scenic;
import cn.sjxy.graduate.entity.dto.ScenicDto;
import cn.sjxy.graduate.service.*;
import cn.sjxy.graduate.utils.ArrayUtil;
import cn.sjxy.graduate.utils.ConditionUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class GraduateApplicationTests {
    @Autowired
    private ScenicService scenicService;
    @Autowired
    private RestaurantDao restaurantDao;
    @Autowired
    private MemberService memberService;

    @Test
    public void test() {

    }

    @Autowired
    private CommentService commentService;

    @Test
    public void test1() {
        Member member = memberService.findById(1);
        System.out.println("member = " + member);
    }

    @Test
    public void test2() {
        ScenicDto scenicDtos = scenicService.selectCommentByScenicId(4);
        if (!StringUtils.isEmpty(scenicDtos.getCommentId())) {
            scenicDtos.setComment(Arrays.asList(scenicDtos.getCommentId().split(",")));
        }
        Condition condition = ConditionUtil.getCondition(Comment.class);
        condition.createCriteria().andIn("id", scenicDtos.getComment());
        List<Comment> comments = commentService.findByCondition(condition);

        for (Comment comment : comments) {
            System.out.println(comment.getUserId());
        }

    }

    @Test
    public void test4() {
        ScenicDto scenicDto = scenicService.selectCommentByScenicId(4);
        System.out.println("scenicDto = " + scenicDto);
        if (!StringUtils.isEmpty(scenicDto.getCommentId())) {
            scenicDto.setComment(Arrays.asList(scenicDto.getCommentId().split(",")));
        }
        Condition condition = ConditionUtil.getCondition(Comment.class);
        condition.createCriteria().andIn("id", scenicDto.getComment());

        List<Comment> comments = commentService.findByCondition(condition);

        ArrayList<Integer> list = new ArrayList<>();
        for (Comment comment : comments) {
            comment.setMemberName(memberService.findBy("id", comment.getUserId()).getName());
            System.out.println(comment.getMemberName());
        }
    }

    @Autowired
    private NewsService newsService;

    @Test
    public void test6() {
        System.out.println("newsService.findAll() = " + newsService.findAll());
    }

    @Test
    public void test7() {
        List<News> five = newsService.findLimitFive();
        for (News news : five) {

        }
    }

    @Test
    public void test8() {
        for (News news : newsService.findLimitFive()) {
            System.out.println("news = " + news);
        }
    }

    @Test
    public void test9() {
        for (Scenic scenic : scenicService.findAll()) {
            System.out.println("scenic = " + scenic);
        }
    }

    @Test
    public void test10() {
        Condition condition = ConditionUtil.getCondition(Scenic.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andLike("name", "%" + "毛衣" + "%");
        Example.Criteria criteria1 = condition.createCriteria();
        criteria1.andLike("details", "%" + "毛衣" + "%");
        condition.or(criteria1);
        List<Scenic> scenics = scenicService.findByCondition(condition);
        for (Scenic scenic : scenics) {
            System.out.println(scenic.getName());
        }
    }

    @Test
    public void test11() {
        String[] arr1 = {new String("" + 32)};
        String applyId = memberService.findById(1).getScenicApplyId();
        String[] split = applyId.split(",");

        String[] minus = ArrayUtil.minus(arr1, split);
        StringBuffer buffer = new StringBuffer();
        for (String s : minus) {
            buffer.append(s+",");
        }
        System.out.println("buffer = " + buffer.toString());
    }
}

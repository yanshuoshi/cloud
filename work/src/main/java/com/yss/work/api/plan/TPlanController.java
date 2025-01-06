package com.yss.work.api.plan;

import com.common.base.BaseController;
import com.common.base.BaseException;
import com.common.base.Response;
import com.common.base.ValidationGroup;
import com.github.pagehelper.PageInfo;
import com.yss.work.api.plan.request.TPlanListReq;
import com.yss.work.api.plan.request.TPlanReq;
import com.yss.work.application.plan.TPlanApplication;
import com.yss.work.application.plan.dto.TPlanUpdateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 锻炼计划
 *
 * @author yss
 * @since 2024-04-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class TPlanController extends BaseController {

    private final TPlanApplication tPlanApplication;

    /**
     * 添加
     *
     * @param req 请求类
     */
    @PostMapping("/add")
    public Response saveTPlan(@RequestBody @Validated({ValidationGroup.Add.class}) TPlanReq req) {
//        int i = 1/0;
        tPlanApplication.addTPlan(req, this.getUserId());
        return Response.ok();
    }

    /**
     * 列表
     *
     * @param req 请求类
     */
    @GetMapping("/list")
    public Response<PageInfo<TPlanUpdateListDto>> TPlanList(@Validated TPlanListReq req) {
        List<TPlanUpdateListDto> list = tPlanApplication.selectTPlanList(req);
        return Response.ok(PageInfo.of(list));
    }

}

package com.jiawa.mytrain.business.controller.admin;

import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.common.resp.PageResp;
import com.jiawa.mytrain.business.req.DailyTrainSeatQueryReq;
import com.jiawa.mytrain.business.req.DailyTrainSeatSaveReq;
import com.jiawa.mytrain.business.resp.DailyTrainSeatQueryResp;
import com.jiawa.mytrain.business.service.DailyTrainSeatService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatAdminController {

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSeatSaveReq req) {
        dailyTrainSeatService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainSeatQueryResp>> queryList(@Valid DailyTrainSeatQueryReq req) {
        PageResp<DailyTrainSeatQueryResp> list = dailyTrainSeatService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainSeatService.delete(id);
        return new CommonResp<>();
    }

}

package com.jiawa.mytrain.business.controller.admin;

import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.common.resp.PageResp;
import com.jiawa.mytrain.business.req.DailyTrainQueryReq;
import com.jiawa.mytrain.business.req.DailyTrainSaveReq;
import com.jiawa.mytrain.business.resp.DailyTrainQueryResp;
import com.jiawa.mytrain.business.service.DailyTrainService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainAdminController {

    @Resource
    private DailyTrainService dailyTrainService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody DailyTrainSaveReq req) {
        dailyTrainService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<DailyTrainQueryResp>> queryList(@Valid DailyTrainQueryReq req) {
        PageResp<DailyTrainQueryResp> list = dailyTrainService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        dailyTrainService.delete(id);
        return new CommonResp<>();
    }

}

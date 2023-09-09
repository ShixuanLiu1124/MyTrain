package com.jiawa.mytrain.business.controller.admin;

import com.jiawa.mytrain.common.context.LoginMemberContext;
import com.jiawa.mytrain.common.resp.CommonResp;
import com.jiawa.mytrain.common.resp.PageResp;
import com.jiawa.mytrain.business.req.SkTokenQueryReq;
import com.jiawa.mytrain.business.req.SkTokenSaveReq;
import com.jiawa.mytrain.business.resp.SkTokenQueryResp;
import com.jiawa.mytrain.business.service.SkTokenService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sk-token")
public class SkTokenAdminController {

    @Resource
    private SkTokenService skTokenService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody SkTokenSaveReq req) {
        skTokenService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<PageResp<SkTokenQueryResp>> queryList(@Valid SkTokenQueryReq req) {
        PageResp<SkTokenQueryResp> list = skTokenService.queryList(req);
        return new CommonResp<>(list);
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        skTokenService.delete(id);
        return new CommonResp<>();
    }

}

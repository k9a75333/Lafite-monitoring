package com.eooker.lafite.modules.keep.web;

import com.eooker.lafite.common.web.BaseController;
import com.eooker.lafite.modules.keep.entity.vo.AppServerVO;
import com.eooker.lafite.modules.keep.param.AppServerParam;
import com.eooker.lafite.modules.keep.service.AppServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xiyatu
 * @date 2018/6/18 19:22
 * Description
 */
@Controller
@RequestMapping(value = "${adminPath}/keep/appServer")
public class AppServerController extends BaseController {

    @Autowired
    private AppServerService appServerService;

    @RequestMapping("/list")
    public String getAppServerList(@RequestParam(value = "type",required = false)Integer type,Model model){

        List<AppServerVO> list = appServerService.getAppServerList(type);
        model.addAttribute("list",list);
        return "modules/keep/appServerList";
    }
    public String addAppServer(@RequestBody @Valid AppServerParam param){

        return "";
    }
}

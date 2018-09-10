/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.keep.web;

import com.eooker.lafite.common.exception.CommonCode;
import com.eooker.lafite.common.exception.CommonResponse;
import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.modules.keep.entity.Server;
import com.eooker.lafite.modules.keep.exception.KeepExceptionCode;
import com.eooker.lafite.modules.keep.param.SSHConfigParam;
import com.eooker.lafite.modules.keep.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.eooker.lafite.common.web.BaseController;

import javax.validation.Valid;

/**
 * 主机服务器
 * @author xiyatu
 * @date 2018/6/18 12:00
 */
@Controller
@RequestMapping(value = "${adminPath}/keep/server")
public class ServerController extends BaseController {

    @Autowired
    private ServerService serverService;

    @RequestMapping("ssh")
    public String ssh(@RequestParam("root") String root, Model model) throws LafiteException {
        model.addAttribute("result", serverService.ssh(root));
        return "modules/keep/ssh";
    }

    @RequestMapping("/list")
    public String getServerList(Model model) {
        model.addAttribute("list", serverService.getServerList());
        return "modules/keep/serverList";
    }

    @RequestMapping("/form")
    public String form(Server server) {
        return "modules/keep/serverForm";
    }

    @RequestMapping("/serverDetail")
    public String serverDetail(Server server, Model model) throws LafiteException {
        model.addAttribute("server", serverService.getServerById(server.getId()));
        return "modules/keep/serverDetail";
    }

    @RequestMapping("/saveServer")
    public String saveServer(Server server, Model model) throws LafiteException {
        serverService.saveServer(server);
        return "redirect:" + adminPath + "/keep/server/list";
    }

    @RequestMapping("/delServer")
    public String delServer(@RequestParam("id") String id) throws LafiteException {
        serverService.deleteServer(id);
        return "redirect:" + adminPath + "/keep/server/list";
    }

    @RequestMapping("/interface/testSSHConnect")
    @ResponseBody
    public CommonResponse testSSHConnect(@RequestBody @Valid SSHConfigParam param) throws LafiteException {
        return new CommonResponse<>(serverService.testSSHConnect(param));
    }
}

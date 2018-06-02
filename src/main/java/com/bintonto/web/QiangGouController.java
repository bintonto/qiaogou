package com.bintonto.web;

import com.bintonto.Exception.QiangGouCloseException;
import com.bintonto.Exception.QiangGouException;
import com.bintonto.Exception.RepeatQiangGouException;
import com.bintonto.dto.Exposer;
import com.bintonto.dto.QiangGouExecution;
import com.bintonto.dto.QiangGouResult;
import com.bintonto.entity.QiangGou;
import com.bintonto.enums.QiangGouStatEnum;
import com.bintonto.service.QiangGouService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/qianggou") // 模块
public class QiangGouController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QiangGouService qiangGouService;          // 肯定不是构造方法注入

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        List<QiangGou> list = qiangGouService.getQiangGouList();

        model.addAttribute("list", list);

        //list.jsp + model = ModelAndView
        return "list"; // /WEB_INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/{qiangGouId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("qiangGouId") Long qiangGouId, Model model) {
        if (qiangGouId == null) {
            return "redirect:/qianggou/list";
        }

        QiangGou qiangGou = qiangGouService.getById(qiangGouId);

        if (qiangGou == null) {
            return "forward:/qianggou/list";
        }

        model.addAttribute("qiangGou", qiangGou);

        return "detail";
    }


    @RequestMapping(value = "{qiangGouId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public QiangGouResult<Exposer> exposer(@PathVariable("qiangGouId") Long qiangGouId) {
        QiangGouResult<Exposer> result;
        try {
            Exposer exposer = qiangGouService.exportQiangGouUrl(qiangGouId);
            result = new QiangGouResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new QiangGouResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "{qiangGouId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public QiangGouResult<QiangGouExecution> execution(@PathVariable("qiangGouId") Long seckillId,
                                                      @CookieValue(value = "killPhone", required = false) Long phone,
                                                      @PathVariable("md5") String md5) {

        if (phone == null) {
            return new QiangGouResult<QiangGouExecution>(false, "未注册");
        }

        try {
            QiangGouExecution execution = qiangGouService.executeQiangGou(seckillId, phone, md5);
            return new QiangGouResult<QiangGouExecution>(true, execution);
        } catch (QiangGouCloseException e) {
            QiangGouExecution execution = new QiangGouExecution(seckillId, QiangGouStatEnum.END);
            return new QiangGouResult<QiangGouExecution>(true, execution);
        } catch (RepeatQiangGouException e) {
            QiangGouExecution execution = new QiangGouExecution(seckillId, QiangGouStatEnum.REPEAT_QiangGou);
            return new QiangGouResult<QiangGouExecution>(true, execution);
        } catch (QiangGouException e) {
            logger.error(e.getMessage(), e);
            QiangGouExecution execution = new QiangGouExecution(seckillId, QiangGouStatEnum.INNER_ERROR);
            return new QiangGouResult<QiangGouExecution>(true, execution);
        }

    }


    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public QiangGouResult<Long> time() {
        Date now = new Date();
        return new QiangGouResult<Long>(true, now.getTime());
    }
}

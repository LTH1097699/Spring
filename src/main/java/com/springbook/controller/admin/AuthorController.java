package com.springbook.controller.admin;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.springbook.conveter.book.AuthorConverter;
import com.springbook.entity.book.AuthorEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbook.dto.book.AuthorDTO;
import com.springbook.service.AuthorService;
import com.springbook.utils.MessageUtils;
import com.springbook.validator.AuthorValidator;

@Controller(value = "authorInAdmin")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private AuthorValidator authorValidate;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(authorValidate);
    }

    @GetMapping(value = "admin/author/list")
    public ModelAndView authorList(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "name", required = false) String keyword,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/authors/list.jsp");
        AuthorDTO authorDTO = new AuthorDTO();

        authorDTO.setPage(Optional.ofNullable(page).orElse(1));
        authorDTO.setLimit(10);

        Pageable pageable = new PageRequest(authorDTO.getPage() - 1, authorDTO.getLimit());

        if (StringUtils.hasText(keyword)) {
            authorDTO.setListResult(authorService.search(keyword, pageable));
            authorDTO.setTotalItem(authorService.totalItemSearch(keyword));

            if (authorDTO.getTotalItem() != 0) {
                authorDTO.setTotalPage((int) Math.ceil((double) authorDTO.getTotalItem() / authorDTO.getLimit()));
            } else {
                authorDTO.setPage(1);
                authorDTO.setTotalPage(1);
                mav.addObject("mess", "Kết quả không tìm thấy");

            }
        } else {
            authorDTO.setListResult(authorService.findAll(pageable).getContent().stream()
                    .map(AuthorConverter::toDTO)
                    .collect(Collectors.toList()));
            authorDTO.setTotalItem(authorService.countTotalItems());

            if (authorDTO.getTotalItem() != 0) {
                authorDTO.setTotalPage((int) Math.ceil((double) authorDTO.getTotalItem() / authorDTO.getLimit()));
            } else {
                mav.addObject("mess", "Chưa có dữ liệu");
                authorDTO.setPage(1);
                authorDTO.setTotalPage(1);
            }

        }
        if (request.getParameter("message") != null) {
            Map<String, String> message = messageUtils.getMessage(request.getParameter("message"));
            mav.addObject("message", message.get("message"));
            mav.addObject("alert", message.get("alert"));
        }
        mav.addObject("model", authorDTO);
        return mav;
    }

    @RequestMapping(value = "/admin/author/create", method = RequestMethod.GET)
    public ModelAndView authorCreate(Model model, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/authors/create.jsp");
        mav.addObject("model", new AuthorDTO());
        return mav;
    }

    @RequestMapping(value = "/admin/author/create", method = RequestMethod.POST)
    public ModelAndView authorCreate(RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("model") AuthorDTO authorDTO, BindingResult result, Errors errors) {
        ModelAndView mav = new ModelAndView("admin/authors/create.jsp");
        if (result.hasErrors()) {
            return mav;
        } else {
            authorService.insert(authorDTO);
            return new ModelAndView("redirect:/admin/author/list?&message=success_save");
        }
    }

    @RequestMapping(value = "/admin/author/edit/{id}", method = RequestMethod.GET)
    public ModelAndView authorEdit(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/authors/edit.jsp");
        AuthorDTO authorDTO = authorService.findById(id);
        mav.addObject("model", authorDTO);
        return mav;
    }

    @RequestMapping(value = "/admin/author/edit/{id}", method = RequestMethod.POST)
    public ModelAndView authorEdit(@Valid @ModelAttribute("model") AuthorDTO authorDTO, BindingResult result,
            @RequestParam(name = "id") Long id) {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("admin/authors/edit.jsp");
            mav.addObject("model", authorDTO);
            return mav;
        } else {
            authorService.update(authorDTO);
            return new ModelAndView("redirect:/admin/author/list?name=&page=1&limit=10&message=success_update");
        }
    }

    @RequestMapping(value = "/admin/author/delete", method = RequestMethod.GET)
    public @ResponseBody ModelAndView authorDelete(@RequestParam(name = "id") Long id, HttpServletRequest request) {
        try {
            authorService.delete(id);
        } catch (ConstraintViolationException | SQLIntegrityConstraintViolationException
                 | DataIntegrityViolationException e) {

            ModelAndView mav = authorList(1, null, request);
            mav.addObject("message", "Xóa không thành công");
            mav.addObject("alert", "danger");
            return mav;
        }
        return new ModelAndView("redirect:/admin/author/list?name=&page=1&limit=10&message=success_delete");

    }
}

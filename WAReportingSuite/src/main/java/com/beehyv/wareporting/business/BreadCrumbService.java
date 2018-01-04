package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.BreadCrumbDto;
import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.model.User;

import java.util.List;

/**
 * Created by beehyv on 29/9/17.
 */
public interface BreadCrumbService {

    List<BreadCrumbDto> getBreadCrumbs(User currentUser, ReportRequest reportRequest);

}

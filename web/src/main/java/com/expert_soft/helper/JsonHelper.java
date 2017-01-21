package com.expert_soft.helper;


import com.expert_soft.model.AjaxResponseCart;

import java.io.IOException;

public interface JsonHelper {

    String write(AjaxResponseCart cart) throws IOException;
}

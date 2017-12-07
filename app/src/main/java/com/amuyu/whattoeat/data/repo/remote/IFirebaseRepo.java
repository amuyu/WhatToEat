package com.amuyu.whattoeat.data.repo.remote;

import com.amuyu.whattoeat.infra.GlideRequest;
import com.amuyu.whattoeat.infra.GlideRequests;

public interface IFirebaseRepo {
    GlideRequest loadImage(GlideRequests glideRequests, String childName);
}

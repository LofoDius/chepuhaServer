package com.sokoldev.chepuhaserver.response;

import com.sokoldev.chepuhaserver.model.Story;

public class StoryResponse extends BaseResponse {
    public Story story;

    public StoryResponse(int code, Story story) {
        super(code);
        this.story = story;
    }
}

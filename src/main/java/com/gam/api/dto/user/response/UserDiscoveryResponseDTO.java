package com.gam.api.dto.user.response;

import com.gam.api.entity.User;

import com.gam.api.entity.Work;
import lombok.Builder;

@Builder
public record UserDiscoveryResponseDTO(
		UserInfoVO UserInfo,
		WorkInfoVO WorkInfo
) {
	public static UserDiscoveryResponseDTO of(User user,boolean designerScrap, Work work){
		return UserDiscoveryResponseDTO.builder()
				.UserInfo(UserInfoVO.of(user,designerScrap))
				.WorkInfo(WorkInfoVO.of(work))
				.build();
	}
}

@Builder
record UserInfoVO(
		Long userId,
		int[] userTag,
		String userName,
		int viewCount,
		String userDetail,
		boolean designerScrap
){
	public static UserInfoVO of(User user, boolean designerScrap){
		return UserInfoVO.builder()
				.userId(user.getId())
				.userTag(user.getTags())
				.userName(user.getUserName())
				.viewCount(user.getViewCount())
				.userDetail(user.getDetail())
				.designerScrap(designerScrap)
				.build();
	}
}

@Builder
record WorkInfoVO(
		Long workId,
		String workTitle,
		String photoUrl
){
	public static WorkInfoVO of (Work work){
		return WorkInfoVO.builder()
				.workId(work.getId())
				.workTitle(work.getTitle())
				.photoUrl(work.getPhotoUrl())
				.build();
	}
}



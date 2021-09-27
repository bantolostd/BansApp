package id.gits.si.bansapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class InstagramResponse(

	@field:SerializedName("logging_page_id")
	val loggingPageId: String? = null,

	@field:SerializedName("profile_pic_edit_sync_props")
	val profilePicEditSyncProps: ProfilePicEditSyncProps? = null,

	@field:SerializedName("seo_category_infos")
	val seoCategoryInfos: List<String?>? = null,

	@field:SerializedName("toast_content_on_load")
	val toastContentOnLoad: @RawValue Any? = null,

	@field:SerializedName("always_show_message_button_to_pro_account")
	val alwaysShowMessageButtonToProAccount: Boolean? = null,

	@field:SerializedName("show_suggested_profiles")
	val showSuggestedProfiles: Boolean? = null,

	@field:SerializedName("graphql")
	val graphql: Graphql? = null,

	@field:SerializedName("show_follow_dialog")
	val showFollowDialog: Boolean? = null,

	@field:SerializedName("show_view_shop")
	val showViewShop: Boolean? = null
) : Parcelable

@Parcelize
data class Owner(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class EdgeFelixDrafts(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class EdgeMediaToCaption(

	@field:SerializedName("edges")
	val edges: List<EdgesItem?>? = null
) : Parcelable

@Parcelize
data class EdgeLikedBy(

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class Location(

	@field:SerializedName("has_public_page")
	val hasPublicPage: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
) : Parcelable

@Parcelize
data class PageInfo(

	@field:SerializedName("has_next_page")
	val hasNextPage: Boolean? = null,

	@field:SerializedName("end_cursor")
	val endCursor: @RawValue Any? = null
) : Parcelable

@Parcelize
data class EdgeMediaToComment(

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class EdgeFelixVideoTimeline(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class EdgesItem(

	@field:SerializedName("node")
	val node: Node? = null
) : Parcelable

@Parcelize
data class EdgeSavedMedia(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: List<EdgesItem?>? = null
) : Parcelable

@Parcelize
data class EdgeFollow(

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class EdgeFelixPendingDraftUploads(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class SharingFrictionInfo(

	@field:SerializedName("bloks_app_url")
	val bloksAppUrl: @RawValue Any? = null,

	@field:SerializedName("should_have_sharing_friction")
	val shouldHaveSharingFriction: Boolean? = null
) : Parcelable

@Parcelize
data class Dimensions(

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null
) : Parcelable

@Parcelize
data class EdgeFollowedBy(

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class EdgeFelixPendingPostUploads(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class ThumbnailResourcesItem(

	@field:SerializedName("src")
	val src: String? = null,

	@field:SerializedName("config_height")
	val configHeight: Int? = null,

	@field:SerializedName("config_width")
	val configWidth: Int? = null
) : Parcelable

@Parcelize
data class EdgeFelixCombinedPostUploads(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class DashInfo(

	@field:SerializedName("is_dash_eligible")
	val isDashEligible: Boolean? = null,

	@field:SerializedName("number_of_qualities")
	val numberOfQualities: Int? = null,

	@field:SerializedName("video_dash_manifest")
	val videoDashManifest: String? = null
) : Parcelable

@Parcelize
data class EdgeMediaToTaggedUser(

	@field:SerializedName("edges")
	val edges:@RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class EdgeFelixCombinedDraftUploads(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("edge_felix_video_timeline")
	val edgeFelixVideoTimeline: EdgeFelixVideoTimeline? = null,

	@field:SerializedName("edge_felix_combined_draft_uploads")
	val edgeFelixCombinedDraftUploads: EdgeFelixCombinedDraftUploads? = null,

	@field:SerializedName("is_business_account")
	val isBusinessAccount: Boolean? = null,

	@field:SerializedName("category_name")
	val categoryName: @RawValue Any? = null,

	@field:SerializedName("has_requested_viewer")
	val hasRequestedViewer: Boolean? = null,

	@field:SerializedName("hide_like_and_view_counts")
	val hideLikeAndViewCounts: Boolean? = null,

	@field:SerializedName("category_enum")
	val categoryEnum: @RawValue Any? = null,

	@field:SerializedName("followed_by_viewer")
	val followedByViewer: Boolean? = null,

	@field:SerializedName("business_address_json")
	val businessAddressJson: @RawValue Any? = null,

	@field:SerializedName("external_url_linkshimmed")
	val externalUrlLinkshimmed: @RawValue Any? = null,

	@field:SerializedName("external_url")
	val externalUrl: @RawValue Any? = null,

	@field:SerializedName("business_phone_number")
	val businessPhoneNumber: @RawValue Any? = null,

	@field:SerializedName("business_category_name")
	val businessCategoryName: @RawValue Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("profile_pic_url")
	val profilePicUrl: String? = null,

	@field:SerializedName("edge_felix_combined_post_uploads")
	val edgeFelixCombinedPostUploads: EdgeFelixCombinedPostUploads? = null,

	@field:SerializedName("follows_viewer")
	val followsViewer: Boolean? = null,

	@field:SerializedName("is_professional_account")
	val isProfessionalAccount: Boolean? = null,

	@field:SerializedName("edge_media_collections")
	val edgeMediaCollections: EdgeMediaCollections? = null,

	@field:SerializedName("blocked_by_viewer")
	val blockedByViewer: Boolean? = null,

	@field:SerializedName("has_blocked_viewer")
	val hasBlockedViewer: Boolean? = null,

	@field:SerializedName("country_block")
	val countryBlock: Boolean? = null,

	@field:SerializedName("is_joined_recently")
	val isJoinedRecently: Boolean? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("highlight_reel_count")
	val highlightReelCount: Int? = null,

	@field:SerializedName("overall_category_name")
	val overallCategoryName: @RawValue Any? = null,

	@field:SerializedName("edge_owner_to_timeline_media")
	val edgeOwnerToTimelineMedia: EdgeOwnerToTimelineMedia? = null,

	@field:SerializedName("business_email")
	val businessEmail: @RawValue Any? = null,

	@field:SerializedName("pronouns")
	val pronouns: @RawValue List<Any?>? = null,

	@field:SerializedName("edge_mutual_followed_by")
	val edgeMutualFollowedBy: EdgeMutualFollowedBy? = null,

	@field:SerializedName("is_private")
	val isPrivate: Boolean? = null,

	@field:SerializedName("has_ar_effects")
	val hasArEffects: Boolean? = null,

	@field:SerializedName("connected_fb_page")
	val connectedFbPage: @RawValue Any? = null,

	@field:SerializedName("has_channel")
	val hasChannel: Boolean? = null,

	@field:SerializedName("edge_felix_pending_post_uploads")
	val edgeFelixPendingPostUploads: EdgeFelixPendingPostUploads? = null,

	@field:SerializedName("profile_pic_url_hd")
	val profilePicUrlHd: String? = null,

	@field:SerializedName("edge_felix_drafts")
	val edgeFelixDrafts: EdgeFelixDrafts? = null,

	@field:SerializedName("requested_by_viewer")
	val requestedByViewer: Boolean? = null,

	@field:SerializedName("should_show_category")
	val shouldShowCategory: Boolean? = null,

	@field:SerializedName("edge_saved_media")
	val edgeSavedMedia: EdgeSavedMedia? = null,

	@field:SerializedName("edge_follow")
	val edgeFollow: EdgeFollow? = null,

	@field:SerializedName("restricted_by_viewer")
	val restrictedByViewer: Boolean? = null,

	@field:SerializedName("edge_felix_pending_draft_uploads")
	val edgeFelixPendingDraftUploads: EdgeFelixPendingDraftUploads? = null,

	@field:SerializedName("biography")
	val biography: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: Boolean? = null,

	@field:SerializedName("has_clips")
	val hasClips: Boolean? = null,

	@field:SerializedName("business_contact_method")
	val businessContactMethod: @RawValue Any? = null,

	@field:SerializedName("edge_followed_by")
	val edgeFollowedBy: EdgeFollowedBy? = null,

	@field:SerializedName("fbid")
	val fbid: String? = null,

	@field:SerializedName("should_show_public_contacts")
	val shouldShowPublicContacts: Boolean? = null,

	@field:SerializedName("has_guides")
	val hasGuides: Boolean? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class EdgeMutualFollowedBy(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class ProfilePicEditSyncProps(

	@field:SerializedName("change_profile_pic_actions_screen_upload_cta")
	val changeProfilePicActionsScreenUploadCta: List<String?>? = null,

	@field:SerializedName("remove_profile_pic_header")
	val removeProfilePicHeader: @RawValue Any? = null,

	@field:SerializedName("remove_profile_pic_subtext")
	val removeProfilePicSubtext: @RawValue Any? = null,

	@field:SerializedName("identity_id")
	val identityId: String? = null,

	@field:SerializedName("change_profile_pic_actions_screen_subheader")
	val changeProfilePicActionsScreenSubheader: List<String?>? = null,

	@field:SerializedName("show_change_profile_pic_confirm_dialog")
	val showChangeProfilePicConfirmDialog: Boolean? = null,

	@field:SerializedName("show_profile_pic_sync_reminders")
	val showProfilePicSyncReminders: Boolean? = null,

	@field:SerializedName("change_profile_pic_actions_screen_remove_cta")
	val changeProfilePicActionsScreenRemoveCta: List<String?>? = null,

	@field:SerializedName("is_business_central_identity")
	val isBusinessCentralIdentity: Boolean? = null,

	@field:SerializedName("remove_profile_pic_confirm_cta")
	val removeProfilePicConfirmCta: @RawValue Any? = null,

	@field:SerializedName("change_profile_pic_actions_screen_header")
	val changeProfilePicActionsScreenHeader: List<String?>? = null,

	@field:SerializedName("remove_profile_pic_cancel_cta")
	val removeProfilePicCancelCta: @RawValue Any? = null,

	@field:SerializedName("change_profile_pic_actions_screen_cancel_cta")
	val changeProfilePicActionsScreenCancelCta: List<String?>? = null
) : Parcelable

@Parcelize
data class Node(

	@field:SerializedName("display_url")
	val displayUrl: String? = null,

	@field:SerializedName("has_upcoming_event")
	val hasUpcomingEvent: Boolean? = null,

	@field:SerializedName("edge_media_to_comment")
	val edgeMediaToComment: EdgeMediaToComment? = null,

	@field:SerializedName("thumbnail_src")
	val thumbnailSrc: String? = null,

	@field:SerializedName("__typename")
	val typename: String? = null,

	@field:SerializedName("shortcode")
	val shortcode: String? = null,

	@field:SerializedName("edge_liked_by")
	val edgeLikedBy: EdgeLikedBy? = null,

	@field:SerializedName("gating_info")
	val gatingInfo: @RawValue Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("fact_check_information")
	val factCheckInformation: @RawValue Any? = null,

	@field:SerializedName("comments_disabled")
	val commentsDisabled: Boolean? = null,

	@field:SerializedName("fact_check_overall_rating")
	val factCheckOverallRating: @RawValue Any? = null,

	@field:SerializedName("coauthor_producers")
	val coauthorProducers: @RawValue List<Any?>? = null,

	@field:SerializedName("thumbnail_resources")
	val thumbnailResources: List<ThumbnailResourcesItem?>? = null,

	@field:SerializedName("edge_media_preview_like")
	val edgeMediaPreviewLike: EdgeMediaPreviewLike? = null,

	@field:SerializedName("owner")
	val owner: Owner? = null,

	@field:SerializedName("taken_at_timestamp")
	val takenAtTimestamp: Int? = null,

	@field:SerializedName("accessibility_caption")
	val accessibilityCaption: String? = null,

	@field:SerializedName("edge_media_to_tagged_user")
	val edgeMediaToTaggedUser: EdgeMediaToTaggedUser? = null,

	@field:SerializedName("sharing_friction_info")
	val sharingFrictionInfo: SharingFrictionInfo? = null,

	@field:SerializedName("media_overlay_info")
	val mediaOverlayInfo: @RawValue Any? = null,

	@field:SerializedName("is_video")
	val isVideo: Boolean? = null,

	@field:SerializedName("media_preview")
	val mediaPreview: String? = null,

	@field:SerializedName("location")
	val location: @RawValue Any? = null,

	@field:SerializedName("edge_media_to_caption")
	val edgeMediaToCaption: EdgeMediaToCaption? = null,

	@field:SerializedName("dimensions")
	val dimensions: Dimensions? = null,

	@field:SerializedName("tracking_token")
	val trackingToken: String? = null,

	@field:SerializedName("has_audio")
	val hasAudio: Boolean? = null,

	@field:SerializedName("video_view_count")
	val videoViewCount: Int? = null,

	@field:SerializedName("video_url")
	val videoUrl: String? = null,

	@field:SerializedName("dash_info")
	val dashInfo: DashInfo? = null,

	@field:SerializedName("product_type")
	val productType: String? = null,

	@field:SerializedName("felix_profile_grid_crop")
	val felixProfileGridCrop: @RawValue Any? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("x")
	val X: Double? = null,

	@field:SerializedName("y")
	val Y: Double? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable

@Parcelize
data class Graphql(

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable

@Parcelize
data class EdgeOwnerToTimelineMedia(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

@Parcelize
data class EdgeMediaPreviewLike(

	@field:SerializedName("count")
	val count: Int? = null
) : Parcelable

@Parcelize
data class EdgeMediaCollections(

	@field:SerializedName("page_info")
	val pageInfo: PageInfo? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("edges")
	val edges: @RawValue List<Any?>? = null
) : Parcelable

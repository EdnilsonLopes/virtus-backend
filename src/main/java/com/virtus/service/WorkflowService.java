package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.dto.request.ActivityRequestDTO;
import com.virtus.domain.dto.request.WorkflowRequestDTO;
import com.virtus.domain.dto.response.*;
import com.virtus.domain.entity.*;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.*;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class WorkflowService extends BaseService<Workflow, WorkflowRepository, WorkflowRequestDTO, WorkflowResponseDTO> {

    private final ActionRepository actionRepository;
    private final ActivityRepository activityRepository;
    private final FeatureRepository featureRepository;
    private final RoleRepository roleRepository;
    private final ActivityRoleRepository activityRoleRepository;
    private final FeatureActivityRepository featureActivityRepository;

    public WorkflowService(WorkflowRepository repository,
                           UserRepository userRepository,
                           EntityManagerFactory entityManagerFactory,
                           ActionRepository actionRepository,
                           ActivityRepository activityRepository,
                           FeatureRepository featureRepository,
                           RoleRepository roleRepository,
                           ActivityRoleRepository activityRoleRepository,
                           FeatureActivityRepository featureActivityRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.actionRepository = actionRepository;
        this.activityRepository = activityRepository;
        this.featureRepository = featureRepository;
        this.roleRepository = roleRepository;
        this.activityRoleRepository = activityRoleRepository;
        this.featureActivityRepository = featureActivityRepository;
    }

    @Override
    protected WorkflowResponseDTO parseToResponseDTO(Workflow entity, boolean detailed) {
        WorkflowResponseDTO response = new WorkflowResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setEntityType(parseToEntityTypeEnumResponse(entity));
        response.setStartAt(entity.getStartAt());
        response.setEndAt(entity.getEndAt());
        response.setCreatedAt(entity.getCreatedAt());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        if (detailed)
            response.setActivities(parseToActivitiesResponseDTO(entity.getActivities()));
        return response;
    }

    private static EnumDTO<Enum> parseToEntityTypeEnumResponse(Workflow entity) {
        if (entity.getEntityType() == null) {
            return null;
        }
        return EnumDTO.builder()
                .value(entity.getEntityType())
                .description(Translator.translate(entity.getEntityType().getKey()))
                .build();
    }

    private List<ActivityResponseDTO> parseToActivitiesResponseDTO(List<Activity> activities) {
        if (CollectionUtils.isEmpty(activities)) {
            return new ArrayList<>();
        }
        return activities.stream().map(activity -> parseToActivityResponseDTO(activity)).collect(Collectors.toList());
    }

    private ActivityResponseDTO parseToActivityResponseDTO(Activity activity) {
        ActivityResponseDTO response = new ActivityResponseDTO();
        response.setId(activity.getId());
        response.setStartAt(activity.getStartAt());
        response.setEndAt(activity.getEndAt());
        response.setExpirationTimeDays(activity.getExpirationTimeDays());
        response.setExpirationAction(activity.getExpirationAction() != null ? parseToActionResponseDTO(activity.getExpirationAction()) : null);
        response.setAction(activity.getAction() != null ? parseToActionResponseDTO(activity.getAction()) : null);
        if (!CollectionUtils.isEmpty(activity.getActivityRoles())) {
            response.setRolesStr(generateCommaSeparatedNames(activity.getActivityRoles()));
            response.setRoles(activity.getActivityRoles()
                    .stream()
                    .map(activityRole -> parseToActivityRoleResponseDTO(activityRole))
                    .collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(activity.getFeaturesActivities())) {
            response.setFeatures(activity.getFeaturesActivities()
                    .stream()
                    .map(featureActivity -> parseToFeatureActivityResponseDTO(featureActivity))
                    .collect(Collectors.toList()));
        }
        return response;
    }

    private FeatureActivityResponseDTO parseToFeatureActivityResponseDTO(FeatureActivity featureActivity) {
        FeatureActivityResponseDTO response = new FeatureActivityResponseDTO();
        response.setId(featureActivity.getId());
        response.setFeature(parseToFeatureResponseDTO(featureActivity.getFeature()));
        return response;
    }

    private FeatureResponseDTO parseToFeatureResponseDTO(Feature feature) {
        FeatureResponseDTO response = new FeatureResponseDTO();
        response.setCode(feature.getCode());
        response.setDescription(feature.getDescription());
        response.setName(feature.getName());
        response.setId(feature.getId());
        response.setCreatedAt(feature.getCreatedAt());
        response.setUpdatedAt(feature.getUpdatedAt());
        response.setAuthor(parseToUserResponseDTO(feature.getAuthor()));
        return response;
    }

    private ActivityRoleResponseDTO parseToActivityRoleResponseDTO(ActivityRole activityRole) {
        ActivityRoleResponseDTO response = new ActivityRoleResponseDTO();
        response.setId(activityRole.getId());
        response.setRole(parseToRoleResponseDTO(activityRole.getRole()));
        return response;
    }

    private RoleResponseDTO parseToRoleResponseDTO(Role role) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setDescription(role.getDescription());
        response.setName(role.getName());
        response.setId(role.getId());
        response.setCreatedAt(role.getCreatedAt());
        response.setUpdatedAt(role.getUpdatedAt());
        return response;
    }

    public static String generateCommaSeparatedNames(List<ActivityRole> roles) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < roles.size(); i++) {
            stringBuilder.append(roles.get(i).getRole().getName());

            if (i < roles.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    private ActionResponseDTO parseToActionResponseDTO(Action entity) {
        entity = actionRepository.findById(entity.getId()).orElse(null);
        if (entity == null) {
            return null;
        }
        ActionResponseDTO response = new ActionResponseDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setOriginStatus(parseToStatusResponseDTO(entity.getOriginStatus()));
        response.setDestinationStatus(parseToStatusResponseDTO(entity.getDestinationStatus()));
        response.setOtherThan(entity.isOtherThan());
        return response;
    }

    @Override
    protected Workflow parseToEntity(WorkflowRequestDTO body) {
        Workflow workflow = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Workflow()) : new Workflow();

        workflow.setName(body.getName());
        workflow.setDescription(body.getDescription());
        workflow.setStartAt(body.getStartAt());
        workflow.setEndAt(body.getEndAt());
        workflow.setEntityType(body.getEntityType() != null ? body.getEntityType().getValue() : null);
        workflow.getActivities().clear();
        workflow.getActivities().addAll(parseToActivities(body.getActivities(), workflow));

        return workflow;
    }

    private List<Activity> parseToActivities(List<ActivityRequestDTO> activities, Workflow workflow) {
        if (CollectionUtils.isEmpty(activities)) {
            return new ArrayList<>();
        }
        return activities.stream().map(activityRequestDTO -> parseToActivityEntity(activityRequestDTO, workflow)).collect(Collectors.toList());
    }

    private Activity parseToActivityEntity(ActivityRequestDTO requestDTO, Workflow workflow) {
        Activity activity = new Activity();
        activity.setId(requestDTO.getId());
        activity.setWorkflow(workflow);
        activity.setStartAt(requestDTO.getStartAt());
        activity.setEndAt(requestDTO.getEndAt());
        activity.setExpirationTimeDays(requestDTO.getExpirationTimeDays());
        if (requestDTO.getActionId() != null)
            activity.setAction(actionRepository.findById(requestDTO.getActionId()).orElse(null));
        if (requestDTO.getExpirationActionId() != null)
            activity.setExpirationAction(actionRepository.findById(requestDTO.getExpirationActionId()).orElse(null));
        activity.getActivityRoles().clear();
        activity.getActivityRoles().addAll(parseToActivityRoles(requestDTO, activity));
        activity.getFeaturesActivities().clear();
        activity.getFeaturesActivities().addAll(parseToFeaturesActivities(requestDTO, activity));
        return activity;
    }

    private List<FeatureActivity> parseToFeaturesActivities(ActivityRequestDTO activityRequestDTO, Activity activity) {
        if (CollectionUtils.isEmpty(activityRequestDTO.getFeatures())) {
            return new ArrayList<>();
        }
        return activityRequestDTO.getFeatures().stream().map(feature -> {
            FeatureActivity activityRole = new FeatureActivity();
            activityRole.setId(feature.getId());
            activityRole.setFeature(featureRepository.findById(
                            feature.getFeature().getId())
                    .orElseThrow(() -> new VirtusException(Translator.translate("feature.not.found"))));
            activityRole.setActivity(activity);
            return activityRole;
        }).collect(Collectors.toList());
    }

    private List<ActivityRole> parseToActivityRoles(ActivityRequestDTO activityRequestDTO, Activity activity) {
        if (CollectionUtils.isEmpty(activityRequestDTO.getRoles())) {
            return new ArrayList<>();
        }

        return activityRequestDTO.getRoles().stream().map(activityRoleRequestDTO -> {
            ActivityRole activityRole = new ActivityRole();
            activityRole.setId(activityRoleRequestDTO.getId());
            activityRole.setRole(roleRepository.findById(
                            activityRoleRequestDTO.getRole().getId())
                    .orElseThrow(() -> new VirtusException(Translator.translate("role.not.found"))));
            activityRole.setActivity(activity);
            return activityRole;
        }).collect(Collectors.toList());
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("workflow.not.found");
    }

    @Override
    protected void beforeCreate(Workflow entity) {
        setDetailsId(entity);
    }

    private void setDetailsId(Workflow entity) {
        if (!CollectionUtils.isEmpty(entity.getActivities())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(activityRepository.findMaxId());

            AtomicReference<Integer> maxIdActivityRole = new AtomicReference<>(activityRoleRepository.findMaxId());
            AtomicReference<Integer> maxIdFeatureActivity = new AtomicReference<>(featureActivityRepository.findMaxId());

            entity.getActivities().forEach(activity -> {
                if (activity.getId() == null) {
                    activity.setId(maxId.updateAndGet(v -> v + 1));
                }
                if (!CollectionUtils.isEmpty(activity.getFeaturesActivities())) {
                    activity.getFeaturesActivities().forEach(featureActivity -> {
                        if (featureActivity.getId() == null) {
                            featureActivity.setId(maxIdFeatureActivity.updateAndGet(v -> v + 1));
                        }
                    });
                }
                if (!CollectionUtils.isEmpty(activity.getActivityRoles())) {
                    activity.getActivityRoles().forEach(activityRole -> {
                        if (activityRole.getId() == null) {
                            activityRole.setId(maxIdActivityRole.updateAndGet(v -> v + 1));
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void beforeUpdate(Workflow entity) {

        setDetailsId(entity);
    }

    private static void verifyFeaturesActivitiesAreUpdated(Workflow entity, Activity persistedActivity) {
        if (!CollectionUtils.isEmpty(persistedActivity.getFeaturesActivities())) {
            Activity activity = entity.getActivities().stream()
                    .filter(a -> a.getId() != null && persistedActivity.getId().equals(a.getId()))
                    .findFirst()
                    .orElse(null);
            if (activity != null && !CollectionUtils.isEmpty(activity.getFeaturesActivities())) {
                activity.getFeaturesActivities().forEach(featureActivity -> {
                    FeatureActivity persistedFeatureActivity = persistedActivity.getFeaturesActivities()
                            .stream()
                            .filter(pa -> pa.getFeature().getId().equals(featureActivity.getFeature().getId()))
                            .findFirst()
                            .orElse(null);
                    if (persistedFeatureActivity != null) {
                        featureActivity.setId(persistedFeatureActivity.getId());
                    }
                });
            }
        }
    }

    private static void verifyActivitiesRolesAreUpdated(Workflow entity, Activity persistedActivity) {
        if (!CollectionUtils.isEmpty(persistedActivity.getActivityRoles())) {
            Activity activity = entity.getActivities().stream()
                    .filter(a -> a.getId() != null && persistedActivity.getId().equals(a.getId()))
                    .findFirst()
                    .orElse(null);
            if (activity != null && !CollectionUtils.isEmpty(activity.getActivityRoles())) {
                activity.getActivityRoles().forEach(activityRole -> {
                    ActivityRole persistedActivityRole = persistedActivity.getActivityRoles()
                            .stream()
                            .filter(pa -> pa.getRole().getId().equals(activityRole.getRole().getId()))
                            .findFirst()
                            .orElse(null);
                    if (persistedActivityRole != null) {
                        activityRole.setId(persistedActivityRole.getId());
                    }
                });
            }
        }
    }
}

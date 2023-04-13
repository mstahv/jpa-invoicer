/*
 * Copyright 2000-2021 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.example.auth;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.example.backend.User;
import org.example.backend.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

@ApplicationScoped
public class ViewAccessChecker {

    @Inject
    UserSession userSession;

    public ViewAccessChecker() {
    }

    /*
     * This method intercepts all requests to navigate to views for access
     * control. It is modified from the ViewAccessChecker in vaadin-server
     * package, which is used by the built-in Spring Security support.
     * In this example all views are allowed, if logged in. If not
     * user is relocated to LoginView. There is also commented out code
     * example and methods to implement role based security.
     */
    public void beforeEnter(@Observes BeforeEnterEvent beforeEnterEvent) {
        Class<?> targetView = beforeEnterEvent.getNavigationTarget();

        // Always allow navigating to LoginView
        if (targetView == LoginView.class) {
            return;
        }

        if(!hasAccess(targetView)) {
            getLogger().debug("Denied access to view {}", targetView.getName());
            if (!userSession.isLoggedIn()) {
                beforeEnterEvent.forwardTo(LoginView.class);
            } else if (isProductionMode(beforeEnterEvent)) {
                // Intentionally does not reveal if the route exists
                beforeEnterEvent.rerouteToError(NotFoundException.class);
            } else {
                beforeEnterEvent.rerouteToError(NotFoundException.class,
                        "Access denied");
            }
        }

    }

    private boolean isProductionMode(BeforeEnterEvent beforeEnterEvent) {
        return beforeEnterEvent.getUI().getSession().getConfiguration()
                .isProductionMode();
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    private boolean hasAccess(Class<?> targetView) {
        User user = userSession.getUser();
        if (user == null) {
            return false;
        }
        // In this example app all views are available for logged-in users
        return true;

        // Below is a code snippet suitable for "role based security"
        // Modifying this example also for permission based security should be easy
        // by changing this implementation.
        /*

        AnnotatedElement annotatedClassOrMethod = getSecurityTarget(targetView);
        if (annotatedClassOrMethod.isAnnotationPresent(DenyAll .class)) {
            return false;
        }
        if (annotatedClassOrMethod
                .isAnnotationPresent(AnonymousAllowed .class)) {
            return true;
        }
        RolesAllowed rolesAllowed = annotatedClassOrMethod
                .getAnnotation(RolesAllowed.class);
        if (rolesAllowed == null) {
            return annotatedClassOrMethod.isAnnotationPresent(PermitAll.class);
        } else {
            for (int i = 0; i < rolesAllowed.value().length; i++) {
                String role = rolesAllowed.value()[i];
                if(user.getRoles().contains(role)) {
                    return true;
                }
            }
            return false;
        }

         */
    }

    /**
     * This is copied from vaadin-server module. Gets the class to check for security restrictions.
     *
     * @param cls
     *            the class to check
     * @return the first annotated class in {@code cls}'s hierarchy that
     *         annotated with one of the access annotations, starting from the
     *         input {@code cls} class itself, going up in the hierarchy.
     *         <em>Note:</em> interfaces in the {@code cls}'s hierarchy are
     *         ignored.
     *         <p>
     *         If no class in the hierarchy was annotated with any of the access
     *         annotations, the {@code cls} input parameter itself would be
     *         returned.
     *         <p>
     *         Access annotations that being checked are:
     *         <ul>
     *         <li>{@code @AnonymousAllowed}
     *         <li>{@code @PermitAll}
     *         <li>{@code @RolesAllowed}
     *         <li>{@code @DenyAll}
     *         </ul>
     *
     * @throws NullPointerException
     *             if the input {@code cls} is null
     */
    private static AnnotatedElement getSecurityTarget(Class<?> cls) {
        Objects.requireNonNull(cls, "The input Class must not be null.");

        Class<?> clazz = cls;
        while (clazz != null && clazz != Object.class) {
            if (hasSecurityAnnotation(clazz)) {
                return clazz;
            }
            clazz = clazz.getSuperclass();
        }
        return cls;
    }

    private static boolean hasSecurityAnnotation(AnnotatedElement method) {
        return method.isAnnotationPresent(AnonymousAllowed.class)
                || method.isAnnotationPresent(PermitAll.class)
                || method.isAnnotationPresent(DenyAll.class)
                || method.isAnnotationPresent(RolesAllowed.class);
    }
}

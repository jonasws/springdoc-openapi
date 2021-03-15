/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package test.org.springdoc.ui.app5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;
import test.org.springdoc.ui.AbstractSpringDocTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = {"server.forward-headers-strategy=framework", "springdoc.cache.disabled=true"})
public class SpringDocOauthRedirectUrlRecalculateTest extends AbstractSpringDocTest {

	@Test
	public void oauth2_redirect_url_recalculation() throws Exception {
		mockMvc.perform(get("/v3/api-docs/swagger-config").header("X-Forwarded-Proto", "https").header("X-Forwarded-Host", "host1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("oauth2RedirectUrl", equalTo("https://host1/swagger-ui/oauth2-redirect.html")));

		mockMvc.perform(get("/v3/api-docs/swagger-config").header("X-Forwarded-Proto", "http").header("X-Forwarded-Host", "host2:8080"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("oauth2RedirectUrl", equalTo("http://host2:8080/swagger-ui/oauth2-redirect.html")));
	}

	@SpringBootApplication
	static class SpringDocTestApp {
	}

}
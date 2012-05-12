//
// Copyright 2012 Vibul Imtarnasan, David Bolton and Socko contributors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.mashupbots.socko.context

import scala.collection.JavaConversions._
import java.util.Map
import org.jboss.netty.handler.codec.http.HttpVersion

/**
 * Details of the initial HTTP request that triggered HTTP chunk or WebSocket processing.
 *
 * This is used for logging and preparing the response
 *
 * @param endPoint HTTP end point used by the request
 * @param isKeepAlive `True` if and only if this connection is to be kept alive
 * @param acceptedEncodings Array of accepted encoding for content compression from the HTTP header
 * @param httpVersion HTTP version being used
 * @param headers HTTP headers sent in the initial request
 */
case class InitialHttpRequest(
  endPoint: EndPoint,
  isKeepAlive: Boolean,
  acceptedEncodings: Array[String],
  httpVersion: HttpVersion,
  headers: List[Map.Entry[String, String]]) {

  def this(request: HttpRequestProcessingContext) = this(
    request.endPoint,
    request.isKeepAlive,
    request.acceptedEncodings,
    request.httpVersion,
    request.headers.toList)

  /**
   * Returns the header value with the specified header name.  If there are
   * more than one header value for the specified header name, the first
   * value is returned.
   *
   * @return `Some(String)` or `None` if there is no such header or the header content is
   * an empty string
   */
  def getHeader(name: String): Option[String] = {
    val v = headers.find(h => h.getKey == name)
    if (v.isDefined) Some(v.get.getValue) else None
  }

}

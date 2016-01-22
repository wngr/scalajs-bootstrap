package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.Bootstrap
import org.scalajs.dom

import scalatags.JsDom.all._

case class NavigationTab(name: String, id: String, icon: String, content: dom.Element, active: Boolean = false)

/**
  * Simple bootstrap navigation bar
  * @param barId Navbar id
  */
final class NavigationBar(barId: String = Bootstrap.newId) {
  private val nav = "nav".tag
  private val `data-toggle` = "data-toggle".attr
  private val `data-target` = "data-target".attr

  private val tabContainer: dom.Element = ul(`class` := "nav navbar-nav").render
  private val tabContentContainer: dom.Element = div(id := s"$barId-tabcontent", `class` := "tab-content").render

  /**
    * Appends provided tabs to tab list
    * @param tabs Navbar tabs
    */
  def addTabs(tabs: NavigationTab*): Unit = {
    def renderTab(tab: NavigationTab): Tag = {
      li(
        `class` := (if (tab.active) "active" else ""),
        a(href := s"#$barId-${tab.id}-tab", role := "tab", `data-toggle` := "tab")(
          span(`class` := s"glyphicon glyphicon-${tab.icon}"),
          raw("&nbsp;"),
          tab.name
        )
      )
    }

    for (tab @ NavigationTab(name, tabId, _, content, active) <- tabs) {
      tabContainer.appendChild(renderTab(tab).render)
      tabContentContainer.appendChild(div(id := s"$barId-$tabId-tab", role := "tabpanel", `class` := (if (active) "tab-pane active fade in" else "tab-pane fade"))(
        content
      ).render)
    }
  }

  /**
    * Updates tab list
    * @param tabs Navbar tabs
    */
  def setTabs(tabs: NavigationTab*): Unit = {
    tabContainer.innerHTML = ""
    tabContentContainer.innerHTML = ""
    this.addTabs(tabs:_*)
  }

  def navbar(brand: String, classes: Seq[String] = Seq("navbar-fixed-top")): Tag = {
    nav(`class` := (Seq("navbar", "navbar-default") ++ classes).mkString(" "))(
      div(`class` := "container")(
        // Header
        div(`class` := "navbar-header")(
          button(`type` := "button", `data-toggle` := "collapse", `data-target` := s"#$barId", `class` := "navbar-toggle collapsed")(
            span(`class` := "sr-only", "Toggle navigation"),
            span(`class` := "icon-bar"),
            span(`class` := "icon-bar"),
            span(`class` := "icon-bar")
          ),
          a(href := "#", `class` := "navbar-brand", brand)
        ),
        div(id := barId, `class` := "navbar-collapse collapse")(
          tabContainer
        )
      )
    )
  }

  def content: dom.Element = tabContentContainer
}
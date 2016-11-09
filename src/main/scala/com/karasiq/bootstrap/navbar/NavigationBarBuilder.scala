package com.karasiq.bootstrap.navbar

import com.karasiq.bootstrap.BootstrapImplicits._
import rx._

import scalatags.JsDom.all._

case class NavigationBarBuilder(tabs: Seq[NavigationTab], barId: String, brand: Modifier, nonTabContent: Modifier, styles: Seq[NavigationBarStyle], container: Modifier ⇒ Modifier, contentContainer: Modifier ⇒ Modifier) {
  def withTabs(tabs: NavigationTab*) = copy(tabs = tabs)
  def withId(id: String) = copy(barId = id)
  def withBrand(brand: Modifier*) = copy(brand = brand)
  def withStyles(styles: NavigationBarStyle*) = copy(styles = styles)
  def withContainer(container: Modifier ⇒ Modifier) = copy(container = container)
  def withContentContainer(contentContainer: Modifier ⇒ Modifier) = copy(contentContainer = contentContainer)
  def withNonTabContent(nonTabContent: Modifier*) = copy(nonTabContent = nonTabContent)

  def build()(implicit ctx: Ctx.Owner) = {
    val bar = new NavigationBar(barId, brand, nonTabContent, styles, container, contentContainer)
    bar.addTabs(tabs:_*)
    bar
  }
}

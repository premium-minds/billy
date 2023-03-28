<?xml version="1.0" encoding="utf-8"?>
<!--

    Copyright (C) 2017 Premium Minds.

    This file is part of billy andorra (AD Pack).

    billy andorra (AD Pack) is free software: you can redistribute it and/or modify it under
    the terms of the GNU Lesser General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option) any
    later version.

    billy andorra (AD Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
    WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
    A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
    details.

    You should have received a copy of the GNU Lesser General Public License
    along with billy andorra (AD Pack). If not, see <http://www.gnu.org/licenses/>.

-->
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo">
	<xsl:template match="creditnote">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4"
					page-height="297mm" page-width="210mm" margin-top="5mm"
					margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body margin-top="1cm" margin-left="1.5cm"
						margin-right="1.5cm" margin-bottom="1.5cm" />
					<fo:region-before extent="0.5cm" />
					<fo:region-after extent="0.5cm" />
					<fo:region-start extent="0.5cm" />
					<fo:region-end extent="0.5cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4">
				<fo:flow flow-name="xsl-region-body">
					<!--business info block -->
					<fo:block font-size="9pt">
						<fo:block>
							<fo:external-graphic height="1.5cm"
								content-width="4cm" src="{concat('url(', ./business/logoPath, ')')}">
							</fo:external-graphic>
						</fo:block>
						<fo:block font-weight="bold" padding-bottom="5mm">
							<xsl:value-of select="./business/name" />
						</fo:block>
						<!--Address -->
						<xsl:value-of select="./business/address/details" />
        				<fo:block />
						<xsl:value-of select="./business/address/postalcode" />
						&#xa0;
						<xsl:value-of select="./business/address/city" />
						<fo:block />
						NIF:
						<xsl:value-of select="./business/financialId" />

						<!-- business contacts -->
						<fo:block font-size="8pt">
							Tel:
							<xsl:value-of select="./business/contacts/phNo" />
							-
							Fax:
							<xsl:value-of select="./business/contacts/faxNo" />
							<fo:block />
							Email:
							<xsl:value-of select="./business/contacts/email" />
						</fo:block>
					</fo:block>

					<!-- customer info -->
					<fo:block margin-top="2mm" font-size="9pt">
						<fo:table width="99%">
							<fo:table-column column-width="50%" />
							<fo:table-column column-width="48%" />
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell display-align="after">
										<fo:block font-size="8pt" margin-bottom="3mm">
											<xsl:choose>
												<xsl:when test="./customer/id">
													Cliente nº:
													<xsl:value-of select="./customer/id" />
												</xsl:when>
											</xsl:choose>
											<fo:block />
											NIF:
											<xsl:value-of select="./customer/financialId" />
											<fo:block />
											<xsl:choose>
												<xsl:when test="paymentMechanism">
													Mitjà de Pagament:
													<xsl:value-of select="paymentMechanism" />
												</xsl:when>
											</xsl:choose>
											<fo:block />
											<xsl:choose>
												<xsl:when test="paymentSettlement">
													Condicions de Pagament:
													<xsl:value-of select="paymentSettlement" />
												</xsl:when>
											</xsl:choose>
										</fo:block>
									</fo:table-cell>
									<!--customer address block -->
									<xsl:choose>
										<xsl:when test="./customer/address">
											<fo:table-cell>
												<fo:block padding="2mm" border-width="1px"
													border-style="solid">
													<xsl:value-of select="./customer/name" />
													<fo:block margin-top="1mm" />
													<xsl:value-of select="./customer/address/details" />
													<fo:block />
													<xsl:value-of select="./customer/address/postalcode" />
													<xsl:choose>
														<xsl:when test="./customer/address/city">
															&#xa0;
															<xsl:value-of select="./customer/address/city" />
														</xsl:when>
													</xsl:choose>
													&#xa0;
													<xsl:value-of select="./customer/address/country" />
												</fo:block>
											</fo:table-cell>
										</xsl:when>
									</xsl:choose>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>

					<!--credit note info block -->
					<fo:block margin-top="6mm" margin-bottom="4mm" font-size="9pt">
						<fo:block margin-bottom="2mm" font-weight="bold">
							Nota d'Abonament núm.:
							<xsl:value-of select="id" />
						</fo:block>
						<fo:table width="100%">
							<fo:table-body>
								<!-- descriptions -->
								<fo:table-row text-align="center" font-size="8pt">
									<fo:table-cell text-align="left">
										<fo:block>
											Data d’emissió:
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:choose>
												<xsl:when test="dueDate">
													Data de venciment:
												</xsl:when>
												<xsl:otherwise>
													&#xa0;
												</xsl:otherwise>
											</xsl:choose>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="right">
										<fo:block>
											Total:
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<!-- values -->
								<fo:table-row text-align="center" font-weight="bold"
									border-top="solid 1px">
									<fo:table-cell text-align="left">
										<fo:block>
											<xsl:value-of select="emissionDate" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											<xsl:value-of select="dueDate" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell text-align="right">
										<fo:block>
											<xsl:value-of select="totalPrice" />
											&#8203; &#8364;
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>

					<!--products info block -->
					<fo:block font-size="8pt" margin-top="3mm" margin-bottom="4mm">
						<fo:block font-weight="bold" font-size="6pt">Detalls del pagament</fo:block>
						<fo:table width="100%">
							<fo:table-column column-width="25mm" />
							<fo:table-column column-width="65mm" />
							<fo:table-column column-width="12mm" />
							<fo:table-column column-width="25mm" />
							<fo:table-column column-width="12mm" />
							<fo:table-column column-width="21mm" />
							<fo:table-header>
								<!-- header -->
								<fo:table-row border-bottom-style="solid"
									border-top-style="solid" text-align="center" font-weight="bold">
									<fo:table-cell>
										<fo:block>
											Codi
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Descripció
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Quant.
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Preu Unit.
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											IGI
										</fo:block>
									</fo:table-cell>
									<fo:table-cell>
										<fo:block>
											Total
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-header>
							<fo:table-body border-bottom-style="solid">
								<xsl:for-each select="./entries/entry">
									<fo:table-row vertical-align="middle" height="6mm"
										text-align="center">
										<fo:table-cell padding="1mm" border-right-style="dotted"
											text-align="center">
											<fo:block>
												<xsl:value-of select="id" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="1mm" border-right-style="dotted"
											text-align="left">
											<fo:block vertical-align="middle">
												<xsl:value-of select="description" />
												 - Nota d'Abonament per a la factura núm.
												<xsl:value-of select="./invoice/id" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="1mm" border-right-style="dotted"
											text-align="center">
											<fo:block>
												<xsl:value-of select="qty" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="1mm" border-right-style="dotted"
											text-align="right">
											<fo:block>
												<xsl:value-of select="unitPrice" />
												&#8364;
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="1mm" text-align="center"
											border-right-style="dotted">
											<fo:block>
												<xsl:value-of select="tax" />
											</fo:block>
										</fo:table-cell>
										<fo:table-cell padding="1mm" text-align="right">
											<fo:block>
												<xsl:value-of select="total" />
												&#8364;
											</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</xsl:for-each>
							</fo:table-body>
						</fo:table>
					</fo:block>



					<!--totals table -->
					<fo:block margin-top="15mm">
						<fo:table table-layout="fixed" width="98%">
							<fo:table-column column-width="50%" />
							<fo:table-column column-width="50%" />
							<fo:table-body font-size="8pt">
								<fo:table-row>
									<fo:table-cell margin-right="2mm">
										<!-- tax details table -->
										<fo:table table-layout="fixed" width="100%">
											<fo:table-column column-width="50%" />
											<fo:table-column column-width="35%" />
											<fo:table-column column-width="15%" />
											<fo:table-body>
												<fo:table-row border-bottom-style="solid"
													text-align="center">
													<fo:table-cell>
														<fo:block text-align="left">
															Base Imposable
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block text-align="right">
															Import de l'IGI
														</fo:block>
													</fo:table-cell>
													<fo:table-cell>
														<fo:block>
															Tipus
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												<xsl:for-each select="./taxDetails/detail">
													<fo:table-row>
														<fo:table-cell padding-top="1mm">
															<fo:block text-align="left">
																<xsl:value-of select="baseValue" />
																&#8364;
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-top="1mm">
															<fo:block text-align="right">
																<xsl:value-of select="taxValue" />
																&#8364;
															</fo:block>
														</fo:table-cell>
														<fo:table-cell padding-top="1mm">
															<fo:block text-align="right">
																<xsl:value-of select="tax" />
															</fo:block>
														</fo:table-cell>
													</fo:table-row>
												</xsl:for-each>
											</fo:table-body>
										</fo:table>
									</fo:table-cell>
									<fo:table-cell margin-left="4mm">
										<!-- final totals table -->
										<fo:table table-layout="fixed" width="100%">
											<fo:table-column column-width="70%" />
											<fo:table-column column-width="30%" />
											<fo:table-body>
												<fo:table-row border-bottom="solid">
													<fo:table-cell text-align="left">
														<fo:block>
															Resum de la Nota d'Abonament:
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												<fo:table-row>
													<fo:table-cell padding-top="1mm" text-align="left">
														<fo:block>
															Total Líquid
														</fo:block>
													</fo:table-cell>
													<fo:table-cell padding-top="1mm" text-align="right">
														<fo:block>
															<xsl:value-of select="totalBeforeTax" />
															&#8364;
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												<fo:table-row>
													<fo:table-cell padding-top="1mm" text-align="left">
														<fo:block>
															Total IGI
														</fo:block>
													</fo:table-cell>
													<fo:table-cell padding-top="1mm" text-align="right">
														<fo:block>
															<xsl:value-of select="totalTax" />
															&#8364;
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
												<fo:table-row font-weight="bold" border-top="dotted">
													<fo:table-cell padding-top="1mm" text-align="left">
														<fo:block>
															Total
														</fo:block>
													</fo:table-cell>
													<fo:table-cell padding-top="1mm" text-align="right">
														<fo:block>
															<xsl:value-of select="totalPrice" />
															&#8364;
														</fo:block>
													</fo:table-cell>
												</fo:table-row>
											</fo:table-body>
										</fo:table>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>